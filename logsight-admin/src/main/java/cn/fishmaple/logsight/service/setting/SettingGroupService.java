package cn.fishmaple.logsight.service.setting;

import cn.fishmaple.logsight.config.I18n;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.dao.dto.LogGroupDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldMapper;
import cn.fishmaple.logsight.dao.mapper.LogGroupMapper;
import cn.fishmaple.logsight.exception.DefaultException;
import cn.fishmaple.logsight.object.LogField;
import cn.fishmaple.logsight.object.LogGroup;
import cn.fishmaple.logsight.util.TimeUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SettingGroupService {
    @Autowired
    LogGroupMapper logGroupMapper;
    @Autowired
    LogFieldMapper logFieldMapper;
    @Autowired
    private I18n i18n;
    public List<LogGroup> getPagesLogGroup(Integer page, Integer sortd, String sortType){
        List<LogGroupDTO> logGroupDTOList = null;
        if(page<1){
            page=1;
        }
        if(sortd!=0){
            String sortSQL = " ORDER BY `"+sortType+(sortd==1?"` ASC":"` DESC");
            logGroupDTOList = logGroupMapper.selectSPage((page-1)*10,10,sortSQL);
        }else {
            logGroupDTOList = logGroupMapper.selectAPage((page - 1) * 10, 10);
        }
        TimeUtil.initedFormatter();
        List<LogGroup> list = new ArrayList<>();
        logGroupDTOList.stream().forEach(logGroupDTO -> {
            list.add(new LogGroup()
                    .setId(logGroupDTO.getId())
                    .setCreateTime(TimeUtil.formatTimeUnchecked(logGroupDTO.getCreateTime()))
                    .setName(logGroupDTO.getName())
                    .setStatusStr(getLogStatusStr(logGroupDTO.getStatus()))
                    .setStatus(logGroupDTO.getStatus())
                    .setFieldCount(logGroupDTO.getFieldCount())
                    .setRemarks(logGroupDTO.getRemarks())
            );
        });
        return list;
    }
    public Integer getGroupPages(Integer perCount){
        Integer count =logGroupMapper.count();
        return count/perCount+(count%perCount>0?1:0);
    }
    public String getLogStatusStr(Integer status) {
        if (0 == status) {
            return i18n.getMessage("i18n.setting_loggroup_table_status_closed");
        } else {
            return i18n.getMessage("i18n.setting_loggroup_table_status_open");
        }
    }
    public Boolean saveGroup(LogGroup logGroup) throws DefaultException {
        Assert.notNull(logGroup.getName(),"null path");
        LogGroupDTO logGroupDTO = new LogGroupDTO();
        logGroupDTO.setName(logGroup.getName());
        logGroupDTO.setStatus(1);
        logGroupDTO.setRemarks(logGroup.getRemarks());
        logGroupDTO.setCreateTime(new Date());
        logGroupDTO.setFieldCount(logGroup.getFieldIds().size());
        StringBuilder str = new StringBuilder();
        for (Long i : logGroup.getFieldIds()) {
            str.append(i+",");
        }
        logGroupDTO.setFieldIds(str.toString());
        boolean flag=false;
        try{
            flag = logGroupMapper.addOne(logGroupDTO)>0;
        }catch (Exception e){
            e.printStackTrace();
            throw new DefaultException(-1,i18n.getMessage("i18n.helper_duplicate_name"));
        }
        return flag;
    }

    public Boolean updateGroup(LogGroup logGroup) throws DefaultException {
        Assert.notNull(logGroup.getName(),"null path");
        LogGroupDTO logGroupDTO = new LogGroupDTO();
        logGroupDTO.setName(logGroup.getName());
        logGroupDTO.setId(logGroup.getId());
        logGroupDTO.setRemarks(logGroup.getRemarks());
        logGroupDTO.setTimeline(new Date());
        logGroupDTO.setFieldCount(logGroup.getFieldIds().size());
        StringBuilder str = new StringBuilder();
        for (Long i : logGroup.getFieldIds()) {
            str.append(i+",");
        }
        logGroupDTO.setFieldIds(str.toString());
        boolean flag=false;
        try{
            flag = logGroupMapper.update(logGroupDTO)>0;
        }catch (Exception e){
            e.printStackTrace();
            throw new DefaultException(-1,i18n.getMessage("i18n.helper_duplicate_name"));
        }
        return flag;
    }
    public LogGroup getGroupById(Integer id){
        LogGroupDTO logGroupDTO= logGroupMapper.getGroupById(id);
        List<LogFieldDTO> logFieldDTOS = logFieldMapper.getByInIdList(logGroupDTO.getFieldIds().substring(0,logGroupDTO.getFieldIds().length()-1));
        return new LogGroup().setId(id).setName(logGroupDTO.getName()).setRemarks(logGroupDTO.getRemarks())
                .setLogFields(logFieldDTOS);
    }

    public void removeGroupById(Integer id){
        logGroupMapper.delete(id);
    }

}
