package cn.fishmaple.logsight.dao.dto;

import java.util.Date;

public class LogFieldTreeDTO {
    private Long id;
    private Long fieldId;
    private Long level;
    private Integer parentId;
    private Date lastScan;
    private String name;

    public LogFieldTreeDTO() {
    }

    public LogFieldTreeDTO(Long id, Long fieldId, Long level) {
        this.id = id;
        this.fieldId = fieldId;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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
