package cn.fishmaple.logsight.object;

import java.util.List;

public class LogAnalyseState {
    private Long id;
    private Integer fileId;
    private String startTime;
    private String endTime;
    private Boolean timeAnalyse;
    private Boolean fileAnalyse;
    private List<String> files;
    private String path;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getTimeAnalyse() {
        return timeAnalyse;
    }

    public void setTimeAnalyse(Boolean timeAnalyse) {
        this.timeAnalyse = timeAnalyse;
    }

    public Boolean getFileAnalyse() {
        return fileAnalyse;
    }

    public void setFileAnalyse(Boolean fileAnalyse) {
        this.fileAnalyse = fileAnalyse;
    }
}
