package cn.fishmaple.logsight.dao.dto;

import java.util.Date;

public class LogFieldFileDTO {
    private Integer id;
    private Date timeline;
    private String pathName;
    private Integer fieldId;
    private Integer status;

    public LogFieldFileDTO() {
    }

    public LogFieldFileDTO(Integer fieldId,Date timeline, String pathName) {
        this.timeline = timeline;
        this.pathName = pathName;
        this.fieldId = fieldId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimeline() {
        return timeline;
    }

    public void setTimeline(Date timeline) {
        this.timeline = timeline;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }
}
