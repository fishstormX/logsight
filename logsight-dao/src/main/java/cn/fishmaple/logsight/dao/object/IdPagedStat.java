package cn.fishmaple.logsight.dao.object;

public class IdPagedStat {
    Long startId;
    Long endId;
    String fields;

    public Long getStartId() {
        return startId;
    }

    public void setStartId(Long startId) {
        this.startId = startId;
    }

    public Long getEndId() {
        return endId;
    }

    public void setEndId(Long endId) {
        this.endId = endId;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
