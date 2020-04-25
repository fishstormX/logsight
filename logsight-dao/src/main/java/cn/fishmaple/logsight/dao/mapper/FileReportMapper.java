package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.FileReportDTO;
import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface FileReportMapper {
    @Insert("INSERT INTO report_file (path_name,file_size,timespan) VALUES(#{pathName},#{fileSize},#{timespan})" )
    public Integer addReport(FileReportDTO fileReportDTO);
    @Select("SELECT IFNULL(SUM(file_size)/1024,0) FROM report_file WHERE timespan =#{timespan}")
    public Double getFileSize(Date timespan);
}
