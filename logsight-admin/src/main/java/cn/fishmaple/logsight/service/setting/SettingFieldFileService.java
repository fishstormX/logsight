package cn.fishmaple.logsight.service.setting;

import cn.fishmaple.logsight.config.I18n;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldMapper;
import cn.fishmaple.logsight.exception.DefaultException;
import cn.fishmaple.logsight.object.LogField;
import cn.fishmaple.logsight.util.ThreadLocalUtil;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SettingFieldFileService {
    @Autowired
    private LogFieldMapper logFieldMapper;
    @Autowired
    private LogFieldFileMapper logFieldFileMapper;
    @Autowired
    private I18n i18n;
    public List<LogFieldDTO> getAll(){
        List<LogFieldDTO> list = logFieldMapper.selectAll();
        return list;
    }

    public List<LogField> getPagesLogField(Integer page,Integer sortd,String sortType,int count){
        List<LogFieldDTO> logFieldDTOList = null;
        if(page<1){
            page=1;
        }
        if(sortd!=0){
            String sortSQL = " ORDER BY `"+sortType+(sortd==1?"` ASC":"` DESC");
            logFieldDTOList = logFieldMapper.selectSPage((page-1)*count,count,sortSQL);
        }else {
            logFieldDTOList = logFieldMapper.selectAPage((page - 1) * count, count);
        }
        TimeUtil.initedFormatter();
        List<LogField> list = new ArrayList<>();
        List<Integer> field4scan = new ArrayList<>();
        logFieldDTOList.stream().forEach(logFieldDTO -> {
            if(logFieldDTO.getStatus()==0){
                field4scan.add(logFieldDTO.getId());
            }
            list.add(new LogField()
                    .setId(logFieldDTO.getId())
                    .setCreateTime(TimeUtil.formatTimeUnchecked(logFieldDTO.getCreateTime()))
                    .setPath(logFieldDTO.getPath())
                    .setStatusStr(getLogStatusStr(logFieldDTO.getStatus()))
                    .setStatus(logFieldDTO.getStatus())
                    .setFileCount(logFieldDTO.getFileCount())
                    .setRemarks(logFieldDTO.getRemarks())
                    .setSize(logFieldDTO.getSize()==null?"":(String.format("%.2f", logFieldDTO.getSize())+" M"))
            );
        });
        ThreadLocalUtil.set("logFields4Scan",field4scan);
        return list;
    }

    public List<LogField> getPagesLogField(Integer page,Integer sortd,String sortType){
        return getPagesLogField(page, sortd, sortType,10);
    }

    public Boolean saveField(LogField logField) throws DefaultException {
        Assert.notNull(logField.getPath(),"null path");
        Assert.notNull(logField.getStatus(),"null status");
        LogFieldDTO logFieldDTO = new LogFieldDTO(logField.getPath(),new Date());
        logFieldDTO.setStatus(logField.getStatus());
        logFieldDTO.setRemarks(logField.getRemarks());
        boolean flag=false;
        try{
            flag = logFieldMapper.addOne(logFieldDTO)>0;
        }catch (Exception e){
            throw new DefaultException(-1,i18n.getMessage("i18n.helper_duplicate_path"));
        }
        return flag;
    }

    public void updateField(LogField logField) throws DefaultException {
        Assert.notNull(logField.getPath(),"null path");
        Assert.notNull(logField.getStatus(),"null status");
        LogFieldDTO logFieldDTO = new LogFieldDTO(logField.getPath(),new Date());
        logFieldDTO.setStatus(logField.getStatus());
        logFieldDTO.setRemarks(logField.getRemarks());
        logFieldDTO.setId(logField.getId());
        boolean flag=false;
        try{
            flag = logFieldMapper.update(logFieldDTO)>0;
        }catch (Exception e){
            throw new DefaultException(-1,i18n.getMessage("i18n.helper_duplicate_path"));
        }
        logFieldFileMapper.deleteByFieldId(logField.getId());
    }

    public void deleteField(Integer id) throws DefaultException {
        logFieldMapper.delete(id);
        logFieldFileMapper.deleteByFieldId(id);
    }

    public LogField getFieldInfo(String id){
        Assert.notNull(id,"null id");
        LogFieldDTO logFieldDTO = logFieldMapper.getFieldById(id);
        return new LogField().setId(logFieldDTO.getId())
                .setStatus(logFieldDTO.getStatus())
                .setStatusStr(getLogStatusStr(logFieldDTO.getStatus()))
                .setFileCount(logFieldDTO.getFileCount())
                .setPath(logFieldDTO.getPath())
                .setRemarks(logFieldDTO.getRemarks())
                .setSize(logFieldDTO.getSize()==null?"":(String.format("%.2f", logFieldDTO.getSize())+" M"));
    }

    public Integer getLogfieldPages(Integer perCount){
        Integer count =logFieldMapper.count();
        return count/perCount+(count%perCount>0?1:0);
    }

    public String getLogStatusStr(Integer status){
        if(0==status){
            return i18n.getMessage("i18n.setting_logfield_table_status_scanning");
        }else if(1==status){
            return i18n.getMessage("i18n.setting_logfield_table_status_open");
        }else if(2==status){
            return i18n.getMessage("i18n.setting_logfield_table_status_closed");
        }else{
            return i18n.getMessage("i18n.setting_logfield_table_status_invalid");
        }
    }

}
