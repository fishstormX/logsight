package cn.fishmaple.logsight.dao.dto;

import java.util.Date;

public class LogFieldDTO {
    private Integer id;
    private String path;
    private Date timeline;
    private Integer status;

    public LogFieldDTO() {
    }

    public LogFieldDTO(String path, Date timeline) {
        this.path = path;
        this.timeline = timeline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTimeline() {
        return timeline;
    }

    public void setTimeline(Date timeline) {
        this.timeline = timeline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
