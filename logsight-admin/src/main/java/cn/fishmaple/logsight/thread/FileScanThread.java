package cn.fishmaple.logsight.thread;

import cn.fishmaple.logsight.dao.dto.FileReportDTO;
import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.mapper.FileReportMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.dao.object.PagedStat;
import cn.fishmaple.logsight.service.ApplicationContextProvider;
import cn.fishmaple.logsight.util.FileUtil;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
@DependsOn(value = {"logFieldMapper"})
public class FileScanThread extends Thread{
    LogFieldFileMapper logFieldFileMapper;
    FileReportMapper fileReportMapper;
    @PostConstruct
    public void init(){
       new FileScanThread().start();
    }
    private void initComponents(){
        while (null==logFieldFileMapper||null==fileReportMapper){
            logFieldFileMapper = ApplicationContextProvider.getBean(LogFieldFileMapper.class);
            fileReportMapper = ApplicationContextProvider.getBean(FileReportMapper.class);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void run(){
        initComponents();
        while (true) {
            Long start = 0L;
            PagedStat pagedStat = new PagedStat();
            pagedStat.setStart(start);
            pagedStat.setCount(100L);
            List<LogFieldFileDTO> logFieldFileDTOList = logFieldFileMapper.getDistinctFilesByPaged(pagedStat);
            while(!logFieldFileDTOList.isEmpty()) {
                for(LogFieldFileDTO logFieldFileDTO:logFieldFileDTOList){
                    logFieldFileDTO.setFileSize(FileUtil.getFileLength(logFieldFileDTO.getPathName()));
                    Date earlyHour = TimeUtil.getEarlyHour();
                    //add a file record pre hour
                    if(!logFieldFileDTO.getLastScan().equals(earlyHour)){
                        logFieldFileDTO.setLastScan(earlyHour);
                        logFieldFileDTO.setPrevSize(logFieldFileDTO.getFileSize());
                        logFieldFileMapper.updateFileSizeWithPrev(logFieldFileDTO);
                        FileReportDTO fileReportDTO = new FileReportDTO();
                        fileReportDTO.setPathName(logFieldFileDTO.getPathName());
                        fileReportDTO.setTimespan(earlyHour);
                        fileReportDTO.setFileSize(logFieldFileDTO.getFileSize());
                        fileReportMapper.addReport(fileReportDTO);
                    }else {
                        logFieldFileMapper.updateFileSize(logFieldFileDTO);
                    }
                }
                start+=100;
                pagedStat.setStart(start);
                logFieldFileDTOList = logFieldFileMapper.getDistinctFilesByPaged(pagedStat);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
