package cn.fishmaple.logsight.thread;

import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldMapper;
import cn.fishmaple.logsight.dao.object.IdPagedStat;
import cn.fishmaple.logsight.handler.FileScanHandler;
import cn.fishmaple.logsight.service.ApplicationContextProvider;
import cn.fishmaple.logsight.util.FileUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@DependsOn(value = {"logFieldMapper"})
public class FileScanThread extends Thread{
    LogFieldFileMapper logFieldFileMapper;
    @PostConstruct
    public void init(){
       new FileScanThread().start();
    }
    private void initComponents(){
        while (null==logFieldFileMapper){
            logFieldFileMapper = ApplicationContextProvider.getBean(LogFieldFileMapper.class);
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
            Long startId = 0L;
            IdPagedStat idPagedStat = new IdPagedStat();
            idPagedStat.setStartId(startId);
            idPagedStat.setEndId(startId+100);
            List<LogFieldFileDTO> logFieldFileDTOList = logFieldFileMapper.getDistinctFilesByPaged(idPagedStat);
            while(!logFieldFileDTOList.isEmpty()) {
                idPagedStat.setStartId(startId);
                idPagedStat.setEndId(startId+100);
                for(LogFieldFileDTO logFieldFileDTO:logFieldFileDTOList){
                    logFieldFileDTO.setFileSize(FileUtil.getFileLength(logFieldFileDTO.getPathName()));
                    logFieldFileMapper.updateFileSize(logFieldFileDTO);
                }
                startId+=100;
                logFieldFileDTOList = logFieldFileMapper.getDistinctFilesByPaged(idPagedStat);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
