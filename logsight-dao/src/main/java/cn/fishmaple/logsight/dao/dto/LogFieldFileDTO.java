package cn.fishmaple.logsight.dao.dto;

import java.util.Date;

public class LogFieldFileDTO {
    private Integer id;
    private Date timeline;
    private String pathName;
    private Integer fieldId;
    private Integer status;
    private Long fileSize;
    private Date lastScan;
    private Long prevSize;
    private Integer treeScannedFlag;

    public LogFieldFileDTO() {
    }

    public LogFieldFileDTO(Integer fieldId,Date timeline, String pathName) {
        this.timeline = timeline;
        this.pathName = pathName;
        this.fieldId = fieldId;
    }

    public LogFieldFileDTO(Integer fieldId,Date timeline, String pathName, Date lastScan) {
        this.timeline = timeline;
        this.pathName = pathName;
        this.fieldId = fieldId;
        this.lastScan = lastScan;
    }

    public LogFieldFileDTO(Integer fieldId,Date timeline, String pathName, Date lastScan,Long fileSize,Integer status) {
        this.timeline = timeline;
        this.pathName = pathName;
        this.fieldId = fieldId;
        this.lastScan = lastScan;
        this.fileSize = fileSize;
        this.status = status;
    }

    public Integer getTreeScannedFlag() {
        return treeScannedFlag;
    }

    public void setTreeScannedFlag(Integer treeScannedFlag) {
        this.treeScannedFlag = treeScannedFlag;
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

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getLastScan() {
        return lastScan;
    }

    public void setLastScan(Date lastScan) {
        this.lastScan = lastScan;
    }

    public Long getPrevSize() {
        return prevSize;
    }

    public void setPrevSize(Long prevSize) {
        this.prevSize = prevSize;
    }
}
