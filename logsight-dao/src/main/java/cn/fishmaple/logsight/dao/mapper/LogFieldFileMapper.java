package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import cn.fishmaple.logsight.dao.object.IdPagedStat;
import cn.fishmaple.logsight.dao.provider.IdPagedProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface LogFieldFileMapper {
    @Insert("INSERT INTO `log_field_file` (`field_id`,`path_name`,`timeline`,`status`) " +
            "VALUES(#{fieldId},#{pathName},#{timeline},#{status}) ")
    public Integer addOneFile(LogFieldFileDTO logFieldFileDTO);
    @Select("SELECT path_name FROM `log_field_file` WHERE `id` > #{id} AND `id` < #{id}" )
    public List<String> getFilesById(String id);
    @SelectProvider(type = IdPagedProvider.class ,method = "fieldDistinctFilePaged")
    public List<LogFieldFileDTO> getDistinctFilesByPaged(IdPagedStat idPagedStat);

    @Update("UPDATE `log_field_file` SET file_size = #{fileSize} WHERE path_name = #{pathName}")
    public Integer updateFileSize(LogFieldFileDTO logFieldFileDTO);
    @Delete("DELETE FROM `log_field_file` ff WHERE ff.`field_id` = #{fieldId} ")
    public Integer deleteByFieldId(String fieldId);
}
