package cn.fishmaple.logsight.service;

import cn.fishmaple.logsight.config.I18n;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldMapper;
import cn.fishmaple.logsight.object.LogField;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        logFieldDTOList.stream().forEach(logFieldDTO -> {
            list.add(new LogField()
                .setId(logFieldDTO.getId())
                .setCreateTime(TimeUtil.formatTimeUnchecked(logFieldDTO.getCreateTime()))
                .setPath(logFieldDTO.getPath())
                .setStatusStr(getLogStatusStr(logFieldDTO.getStatus()))
                .setStatus(logFieldDTO.getStatus())
                .setFileCount(logFieldDTO.getFileCount())
            );
        });
        return list;
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
