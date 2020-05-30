package cn.fishmaple.logsight.core;

import cn.fishmaple.logsight.object.FileDownloadStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
@Component
public class FileDownLoadStorage {
    private Map<String, FileDownloadStatus> fileDownloadStatusMap;

    public FileDownloadStatus getFileDownloadStatus(String id) {
        return this.fileDownloadStatusMap.get(id);
    }

    public void addFileDownloadStatus(FileDownloadStatus fileDownloadStatus) {
        this.fileDownloadStatusMap.put(fileDownloadStatus.getId(),fileDownloadStatus);
    }

    @PostConstruct
    public void init(){
        fileDownloadStatusMap = new HashMap<String,FileDownloadStatus>();
    }
}
