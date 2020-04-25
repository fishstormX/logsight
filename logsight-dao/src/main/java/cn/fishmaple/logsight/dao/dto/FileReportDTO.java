package cn.fishmaple.logsight.dao.dto;

import java.util.Date;

public class FileReportDTO {
    private Integer id;
    private String pathName;
    private Long fileSize;
    private Date timespan;

    public FileReportDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getTimespan() {
        return timespan;
    }

    public void setTimespan(Date timespan) {
        this.timespan = timespan;
    }
}
