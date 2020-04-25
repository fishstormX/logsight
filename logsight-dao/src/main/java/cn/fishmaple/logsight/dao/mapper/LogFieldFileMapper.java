package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.object.IdPagedStat;
import cn.fishmaple.logsight.dao.object.PagedStat;
import cn.fishmaple.logsight.dao.provider.IdPagedProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface LogFieldFileMapper {
    @Insert("INSERT INTO `log_field_file` (`field_id`,`path_name`,`timeline`,`status`,`last_scan`) " +
            "VALUES(#{fieldId},#{pathName},#{timeline},#{status},#{lastScan}) ON DUPLICATE KEY UPDATE " +
            "`field_id`=#{fieldId},`path_name` = #{pathName} ,`timeline` = #{timeline}")
    public Integer addOneFile(LogFieldFileDTO logFieldFileDTO);
    @Select("SELECT path_name pathName FROM `log_field_file` WHERE `id` > #{id} AND `id` < #{id}" )
    public List<String> getFilesById(String id);
    @Select("SELECT DISTINCT path_name pathName , last_scan lastScan , prev_size prevSize " +
            "FROM log_field_file ORDER BY `id` LIMIT #{start},#{count}")
    public List<LogFieldFileDTO> getDistinctFilesByPaged(PagedStat PagedStat);
    @SelectProvider(type = IdPagedProvider.class ,method = "fieldDistinctFilePaged")
    public List<LogFieldFileDTO> getDistinctFilesByIdPaged(IdPagedStat idPagedStat);
    @Select("SELECT DISTINCT path_name pathName,file_size fileSize FROM `log_field_file` ORDER BY file_size DESC,id LIMIT #{limit}")
    public List<LogFieldFileDTO> selectMax(Integer limit);
    @Select("SELECT SUM(DISTINCT file_size)/1024 FROM `log_field_file`")
    public Double getTotalSize();
    @Select("SELECT COUNT(DISTINCT path_name) FROM `log_field_file`")
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
