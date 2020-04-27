package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.object.IdPagedStat;
import cn.fishmaple.logsight.dao.object.PagedStat;
import cn.fishmaple.logsight.dao.provider.IdPagedProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface LogFieldFileMapper {
    @Insert("INSERT INTO `log_field_file` (`field_id`,`path_name`,`timeline`,`status`,`last_scan`) " +
            "VALUES(#{fieldId},#{pathName},#{timeline},#{status},#{lastScan}) ON DUPLICATE KEY UPDATE " +
            "`field_id`=#{fieldId},`path_name` = #{pathName} ,`timeline` = #{timeline},`status` = #{status},`file_size` = #{fileSize}")
    public Integer addOneFile(LogFieldFileDTO logFieldFileDTO);
    @Select("SELECT `id`,path_name pathName,file_size fileSize,status  FROM `log_field_file` WHERE `field_id`=#{fieldId}" )
    public Set<LogFieldFileDTO> getFilesByFieldId(Integer fieldId);
    @Select("SELECT DISTINCT path_name pathName , last_scan lastScan , prev_size prevSize " +
            "FROM log_field_file ORDER BY `id` LIMIT #{start},#{count}")
    public List<LogFieldFileDTO> getDistinctFilesByPaged(PagedStat PagedStat);
    @SelectProvider(type = IdPagedProvider.class ,method = "fieldDistinctFilePaged")
    public List<LogFieldFileDTO> getDistinctFilesByIdPaged(IdPagedStat idPagedStat);
    @Select("SELECT DISTINCT path_name pathName,file_size fileSize FROM `log_field_file` ORDER BY file_size DESC,id LIMIT #{limit}")
    public List<LogFieldFileDTO> selectMax(Integer limit);
    @Select("SELECT SUM(file_size)/1024 FROM (SELECT DISTINCT`path_name`,`file_size` FROM `log_field_file` WHERE status = 1 )l")
    public Double getTotalSize();
    @Select("SELECT COUNT(DISTINCT path_name) FROM `log_field_file`  WHERE status = 1")
    public Long getTotalCount();
    @Update("UPDATE `log_field_file` SET file_size = #{fileSize} WHERE path_name = #{pathName}")
    public Integer updateFileSize(LogFieldFileDTO logFieldFileDTO);
    @Update("UPDATE `log_field_file` SET file_size = #{fileSize}," +
            "`last_scan` = #{lastScan},`prev_size` = #{prevSize}" +
            " WHERE path_name = #{pathName}")
    public Integer updateFileSizeWithPrev(LogFieldFileDTO logFieldFileDTO);
    @Delete("DELETE FROM `log_field_file` ff WHERE ff.`field_id` = #{fieldId} ")
    public Integer deleteByFieldId(String fieldId);
}
