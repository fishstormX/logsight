package cn.fishmaple.logsight.service;

import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.mapper.FileReportMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldMapper;
import cn.fishmaple.logsight.object.LogFile;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class ReportService {
    @Autowired
    FileReportMapper fileReportMapper;
    @Autowired
    LogFieldFileMapper logFieldFileMapper;
    @Autowired
    LogFieldMapper logFieldMapper;
    public List<String> getDailyFileReport(){
        Integer nowaHour = TimeUtil.getHours();
        Calendar calendar=Calendar.getInstance();
        List<String> result = new ArrayList<>();
        for(int i=0;i<24+nowaHour+1;i++){
            Double size=fileReportMapper.getFileSize(TimeUtil.getEarlyHour(i-24-nowaHour,0))/1024;
            result.add(String.format("%.2f", size));
        }
        return  result;
    }

    public List<LogFile> getMax(){
        List<LogFieldFileDTO> logFieldFileDTOS = logFieldFileMapper.selectMax(9);
        List<LogFile> result = new ArrayList<>();
        for(LogFieldFileDTO logFieldFileDTO:logFieldFileDTOS){
            Double fileSize = null==logFieldFileDTO.getFileSize()?0:logFieldFileDTO.getFileSize().doubleValue()/(1024*1024);
            result.add(new LogFile().setName(logFieldFileDTO.getPathName()).setSize(String.format("%.2f", fileSize)+" M"));
        }
        return  result;
    }

    public String getTotalFileSize(){
        Double result = logFieldFileMapper.getTotalSize()/1024;
        return String.format("%.2f", result)+"M";
    }

    public Long getTotalFileCount(){
        return logFieldFileMapper.getTotalCount();
    }

    public Integer getTotalFieldCount(){
        return logFieldMapper.availableCount();
    }
}
