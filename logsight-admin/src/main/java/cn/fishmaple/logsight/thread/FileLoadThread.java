package cn.fishmaple.logsight.thread;

import cn.fishmaple.logsight.dao.consts.LogFileStatus;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.dto.LogFieldTreeDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldFileMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldMapper;
import cn.fishmaple.logsight.dao.mapper.LogFieldTreeMapper;
import cn.fishmaple.logsight.handler.FileScanHandler;
import cn.fishmaple.logsight.service.ApplicationContextProvider;
import cn.fishmaple.logsight.util.TimeUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Service
@DependsOn(value = {"logFieldMapper","logFieldFileMapper","fileScanHandler"})
public class FileLoadThread extends Thread{
    LogFieldMapper logFieldMapper;
    LogFieldFileMapper logFieldFileMapper;
    LogFieldTreeMapper logFieldTreeMapper;
    FileScanHandler fileScanHandler;
    Integer scanTimes;
    @PostConstruct
    public void init(){
       new FileLoadThread().start();
    }

    private void initThread(){
        scanTimes = 0;
        while (null==logFieldMapper||null==fileScanHandler||null==logFieldFileMapper||null==logFieldTreeMapper){
            logFieldMapper = ApplicationContextProvider.getBean(LogFieldMapper.class);
            logFieldFileMapper = ApplicationContextProvider.getBean(LogFieldFileMapper.class);
            fileScanHandler = ApplicationContextProvider.getBean(FileScanHandler.class);
            logFieldTreeMapper = ApplicationContextProvider.getBean(LogFieldTreeMapper.class);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void run(){
        initThread();
        while (true) {
            boolean overallScan = (scanTimes>>2)>0;
            List<LogFieldDTO> list;
            List<LogFieldTreeDTO> treeList;
            if(overallScan){
                list = logFieldMapper.selectUnClosedField();
                scanTimes = 0;
            }else{
                list = logFieldMapper.selectUnScannedField();
            }
            treeList = new ArrayList<>();
            for (LogFieldDTO logFieldDTO : list) {
                int count=0;
                Collection<String> files = fileScanHandler.scanFile(logFieldDTO.getPath());
                Set<LogFieldFileDTO> nowaFiles = logFieldFileMapper.getFilesByFieldId(logFieldDTO.getId());
                if(overallScan){
                    logFieldDTO.setFileCount(null);
                    logFieldDTO.setTimeline(new Date());
                    logFieldDTO.setStatus(0);
                    logFieldMapper.scanUpdate(logFieldDTO);
                }
                double size = 0L;
                for (LogFieldFileDTO logFieldFileDTO : nowaFiles) {
                    File file = new File(logFieldFileDTO.getPathName());
                    if(file.isFile()&&!file.isHidden()){
                        if(0==logFieldFileDTO.getTreeScannedFlag()){
                            buildFileTree(logFieldDTO.getId(),logFieldFileDTO.getPathName(),logFieldFileDTO.getId());
                        }
                        files.remove(logFieldFileDTO.getPathName());
                        logFieldFileMapper.addOneFile(new LogFieldFileDTO(logFieldDTO.getId(), new Date(),
                                logFieldFileDTO.getPathName(),TimeUtil.getEarlyHour(-1,0),logFieldFileDTO.getFileSize(), LogFileStatus.NORMAL));
                        count++;
                        size += ((double)file.length())/1024/1024;
                    }else{
                        logFieldFileMapper.addOneFile(new LogFieldFileDTO(logFieldDTO.getId(), new Date(),
                                logFieldFileDTO.getPathName(),TimeUtil.getEarlyHour(-1,0),0L,LogFileStatus.DELETED));

                    }
                }
                for (String logFile : files) {
                    File file = new File(logFile);
                    if(file.isFile()&&!file.isHidden()){
                        logFieldFileMapper.addOneFile(new LogFieldFileDTO(logFieldDTO.getId(), new Date(),
                                logFile,TimeUtil.getEarlyHour(-1,0),0L,LogFileStatus.NORMAL));
                        count++;
                        size += ((double)file.length())/1024/1024;
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
                    logFieldDTO.setTotalSize(size);
                }
                logFieldMapper.scanUpdate(logFieldDTO);
            }
            scanTimes++;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Transactional(rollbackFor = Exception.class)
    void buildFileTree(Integer fieldId, String filePath, Integer fileId){
        String splitor = filePath.indexOf("/")>-1?"/":"\\\\";
        String paths[] = filePath.split(splitor);
        boolean savedFlag = true;
        StringBuilder nowaPath = new StringBuilder();
        Long parentId=0L;
        Long preId=0L;
        for(int i =0;i<paths.length;i++){
            nowaPath.append(paths[i]);
            if(savedFlag) {
                //查询本层
                preId = logFieldTreeMapper.selectId(fieldId,nowaPath.toString());
                // 查询是否存在subPath
                if(null == preId||preId==-1){
                    savedFlag = false;
                }
                //存在 savedFlag变为false
            }
            if(!savedFlag){
                //if not absent
                LogFieldTreeDTO logFieldTreeDTO = new LogFieldTreeDTO(fieldId,i,parentId,new Date(),paths[i],nowaPath.toString());
                logFieldTreeMapper.add(logFieldTreeDTO);
                preId = logFieldTreeDTO.getId();
            }
            parentId = preId;
            if(i<paths.length-1){
                nowaPath.append("/");
            }
        }
        logFieldFileMapper.updateTreeScannedFlag(1,fileId);
    }

}
