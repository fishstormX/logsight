package cn.fishmaple.logsight.dao.dto;

import java.util.Date;

public class LogFieldTreeDTO {
    private Long id;
    private Integer fieldId;
    private Integer level;
    private Long parentId;
    private Date lastScan;
    private String name;
    private String path;

    public LogFieldTreeDTO() {
    }

    public LogFieldTreeDTO(Integer fieldId, Integer level, Long parentId, Date lastScan, String name, String path) {
        this.fieldId = fieldId;
        this.level = level;
        this.parentId = parentId;
        this.lastScan = lastScan;
        this.name = name;
        this.path = path;
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

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getLastScan() {
        return lastScan;
    }

    public void setLastScan(Date lastScan) {
        this.lastScan = lastScan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
