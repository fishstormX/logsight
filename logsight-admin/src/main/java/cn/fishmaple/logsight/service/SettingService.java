package cn.fishmaple.logsight.service;

import cn.fishmaple.logsight.config.I18n;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
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
public class SettingService {
    @Autowired
    private LogFieldMapper logFieldMapper;
    @Autowired
    private I18n i18n;
    public List<LogField> getPagesLogField(Integer page,Integer sortd,String sortType){
        List<LogFieldDTO> logFieldDTOList = null;
        if(page<1){
            page=1;
        }
        if(sortd!=0){
            String sortSQL = " ORDER BY `"+sortType+(sortd==1?"` ASC":"` DESC");
            logFieldDTOList = logFieldMapper.selectSPage((page-1)*10,10,sortSQL);
        }else {
            logFieldDTOList = logFieldMapper.selectAPage((page - 1) * 10, 10);
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
            );
        });
        ThreadLocalUtil.set("logFields4Scan",field4scan);
        return list;
    }

    public Boolean saveField(LogField logField) throws DefaultException {
        Assert.notNull(logField.getPath(),"null path");
        Assert.notNull(logField.getStatus(),"null path");
        LogFieldDTO logFieldDTO = new LogFieldDTO(logField.getPath(),new Date());
        logFieldDTO.setStatus(logField.getStatus());
        new LogFieldDTO().setCreateTime(new Date());
        boolean flag=false;
        try{
            flag = logFieldMapper.addOne(logFieldDTO)>0;
        }catch (Exception e){
            throw new DefaultException(-1,i18n.getMessage("i18n.helper_duplicate_path"));
        }
        return flag;
    }

    public LogField getFieldInfo(String id){
        Assert.notNull(id,"null id");
        LogFieldDTO logFieldDTO = logFieldMapper.getFieldById(id);
        return new LogField().setId(logFieldDTO.getId())
                .setStatus(logFieldDTO.getStatus())
                .setStatusStr(getLogStatusStr(logFieldDTO.getStatus()))
                .setFileCount(logFieldDTO.getFileCount());
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
