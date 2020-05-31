package cn.fishmaple.logsight.object;

import cn.fishmaple.logsight.object.fileAnalyse.BaseFile;

import java.util.List;
import java.util.Map;

public class FileDownloadStatus {
    private Map<String,Integer> fileRate;
    private List<BaseFile> files;
    private List<BaseFile> unSelectFiles;
    private LogAnalyseState logAnalyseState;
    private String id;


    public List<BaseFile> getUnSelectFiles() {
        return unSelectFiles;
    }

    public void setUnSelectFiles(List<BaseFile> unSelectFiles) {
        this.unSelectFiles = unSelectFiles;
    }

    public LogAnalyseState getLogAnalyseState() {
        return logAnalyseState;
    }

    public void setLogAnalyseState(LogAnalyseState logAnalyseState) {
        this.logAnalyseState = logAnalyseState;
    }

    public Map<String,Integer> getFileRate() {
        return fileRate;
    }

    public void setFileRate(Map<String,Integer> fileRate) {
        this.fileRate = fileRate;
    }

    public List<BaseFile> getFiles() {
        return files;
    }

    public void setFiles(List<BaseFile> files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
