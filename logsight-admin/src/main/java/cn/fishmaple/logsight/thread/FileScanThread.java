package cn.fishmaple.logsight.thread;

import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldMapper;
import cn.fishmaple.logsight.handler.FileScanHandler;
import cn.fishmaple.logsight.service.ApplicationContextProvider;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@DependsOn(value = {"logFieldMapper","fileScanHandler"})
public class FileScanThread extends Thread{
    @PostConstruct
    public void init(){
       new FileScanThread().start();
    }
    public void run(){
        LogFieldMapper logFieldMapper;
        FileScanHandler fileScanHandler;
        while (true){
            logFieldMapper = ApplicationContextProvider.getBean(LogFieldMapper.class);
            fileScanHandler = ApplicationContextProvider.getBean(FileScanHandler.class);
            if(null==logFieldMapper||null==fileScanHandler){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                break;
            }
        }
        while (true) {
            List<LogFieldDTO> list = logFieldMapper.selectUnScannedField();
            for (LogFieldDTO logFieldDTO : list) {
                int count=0;
                Collection<String> files = fileScanHandler.scanFile(logFieldDTO.getPath());
                for (String logFile : files) {
                    File file = new File(logFile);
                    if(file.isFile()&&!file.isHidden()){
                        logFieldMapper.addOneFile(new LogFieldFileDTO(logFieldDTO.getId(), new Date(), logFile));
                        count++;
                    }
                }
                if(count<1){
                    logFieldDTO.setFileCount(0);
                    logFieldDTO.setTimeline(new Date());
                    logFieldDTO.setStatus(3);
                }else{
                    logFieldDTO.setFileCount(count);
                    logFieldDTO.setTimeline(new Date());
                    logFieldDTO.setStatus(1);
                }
                logFieldMapper.update(logFieldDTO);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
