package cn.fishmaple.logsight.service.filezone;

import cn.fishmaple.logsight.analyser.fileAnalyser.FileAnalyser;
import cn.fishmaple.logsight.core.FileDownLoadStorage;
import cn.fishmaple.logsight.object.FileDownloadStatus;
import cn.fishmaple.logsight.object.LogAnalyseState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.*;

@Service
public class FileAnalyseService {
    @Autowired
    private FileZoneService fileZoneService;
    @Autowired
    private FileDownLoadStorage fileDownLoadStorage;

    public FileDownloadStatus getStatus(LogAnalyseState logAnalyseState){
        String path = fileZoneService.getNodePath(logAnalyseState.getFileId());
        File file = new File(path);
        String parentPath = file.getParent();
        String [] files = new File(parentPath).list();
        List<String> listTmp = Arrays.asList(files);
        List<String> list = new ArrayList<>();
        Collections.sort(listTmp);
        Map<String,Integer> fileRateMap = new LinkedHashMap<>();
        for(String fileName:listTmp){
            if(new File(parentPath+"/"+fileName).isFile()) {
                fileRateMap.put(parentPath +"/"+ fileName, 0);
                list.add(fileName);
            }
        }
        FileDownloadStatus fileDownloadStatus = new FileDownloadStatus();
        fileDownloadStatus.setFiles(list);
        fileDownloadStatus.setFileRate(fileRateMap);
        fileDownloadStatus.setLogAnalyseState(logAnalyseState);
        String uuid = UUID.randomUUID().toString().replace("-","").replace("16","a");
        fileDownloadStatus.setId(uuid);
        fileDownLoadStorage.addFileDownloadStatus(fileDownloadStatus);
        return fileDownloadStatus;
    }

    public InputStream getInputStream(FileAnalyser fileAnalyser,String pathName) {
       return fileAnalyser.convertFile(pathName);
    }

}
