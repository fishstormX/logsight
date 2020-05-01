package cn.fishmaple.logsight.dao.dto;

import java.util.Date;

public class LogFieldDTO {
    private Integer id;
    private String path;
    private Date createTime;
    private Integer status;
    private Integer fileCount;
    private Date timeline;
    private Double size;

    public Double getSize() {
        return size;
    }

    public void setTotalSize(Double size) {
        this.size = size;
    }

    public LogFieldDTO() {
    }

    public LogFieldDTO(String path, Date createTime) {
        this.path = path;
        this.createTime = createTime;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public Date getTimeline() {
        return timeline;
    }

    public void setTimeline(Date timeline) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
