package cn.fishmaple.logsight.object;

public class LogField {
    private Integer id;
    private String path;
    private String createTime;
    private String statusStr;
    private Integer status;
    private String updateTime;
    private Integer fileCount;

    public LogField() {
    }

    public LogField(Integer id, String path, String createTime, String statusStr) {
        this.id = id;
        this.path = path;
        this.createTime = createTime;
        this.statusStr = statusStr;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public LogField setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public LogField setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public LogField setPath(String path) {
        this.path = path;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public LogField setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public LogField setStatusStr(String statusStr) {
        this.statusStr = statusStr;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public LogField setStatus(Integer status) {
        this.status = status;
        return this;
    }
}
