package cn.fishmaple.logsight.object;

public class LogField {
    private Integer id;
    private String path;
    private String time;
    private String statusStr;
    private Integer status;

    public LogField() {
    }

    public LogField(Integer id, String path, String time, String statusStr) {
        this.id = id;
        this.path = path;
        this.time = time;
        this.statusStr = statusStr;
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

    public String getTime() {
        return time;
    }

    public LogField setTime(String time) {
        this.time = time;
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
