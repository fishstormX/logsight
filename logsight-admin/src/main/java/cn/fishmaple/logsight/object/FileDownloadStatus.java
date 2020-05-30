package cn.fishmaple.logsight.object;

import java.util.List;
import java.util.Map;

public class FileDownloadStatus {
    private Map<String,Integer> fileRate;
    private List<String> files;
    private LogAnalyseState logAnalyseState;
    private String id;

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

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
