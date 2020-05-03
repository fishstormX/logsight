package cn.fishmaple.logsight.object;

import cn.fishmaple.logsight.dao.dto.LogFieldDTO;

import java.util.List;

public class LogGroup {
    private Integer id;
    private String name;
    private String createTime;
    private String statusStr;
    private Integer status;
    private String updateTime;
    private Integer fieldCount;
    private List<Long> fieldIds;
    private String remarks;
    private List<LogFieldDTO> logFields;

    public String getRemarks() {
        return remarks;
    }

    public LogGroup setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public LogGroup() {
    }

    public LogGroup(Integer id, String name, String createTime, String statusStr) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.statusStr = statusStr;
    }

    public List<LogFieldDTO> getLogFields() {
        return logFields;
    }

    public LogGroup setLogFields(List<LogFieldDTO> logFieldDTOS) {
        this.logFields = logFieldDTOS;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public LogGroup setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LogGroup setName(String name) {
        this.name = name;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public LogGroup setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public LogGroup setStatusStr(String statusStr) {
        this.statusStr = statusStr;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public LogGroup setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public LogGroup setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getFieldCount() {
        return fieldCount;
    }

    public LogGroup setFieldCount(Integer fieldCount) {
        this.fieldCount = fieldCount;
        return this;
    }

    public List<Long> getFieldIds() {
        return fieldIds;
    }

    public LogGroup setFieldIds(List<Long> fieldIds) {
        this.fieldIds = fieldIds;
        return this;
    }
}
