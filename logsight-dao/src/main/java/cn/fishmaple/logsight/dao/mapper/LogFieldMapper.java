package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.dao.dto.LogFieldFileDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LogFieldMapper {
    @Select("SELECT 1")
    public Integer checkConnect();
    @Insert("INSERT INTO `log_field` (`path`,`create_time`,`status`) VALUES(#{path},#{createTime},#{status}) ")
    public Integer addOne(LogFieldDTO logFieldDTO);
    @Update("UPDATE `log_field` SET timeline = #{timeline},file_count = #{fileCount},`status` =#{status} WHERE `id` = #{id}")
    public Integer update(LogFieldDTO logFieldDTO);
    @Select("SELECT `id`,`path`,`status` FROM `log_field` WHERE `status` = 0")
    public List<LogFieldDTO> selectUnScannedField();
    @Select("SELECT COUNT(*) FROM `log_field`")
    public Integer count();
    @Select("SELECT `id`,`path`,`timeline`,`status`,`create_time` createTime,`file_count` fileCount FROM `log_field` LIMIT #{start},#{count}")
    public List<LogFieldDTO> selectAPage(@Param("start") int start, @Param("count")int count);
    @Select("SELECT `id`,`path`,`timeline`,`status`,`create_time` createTime,`file_count` fileCount FROM `log_field` " +
            "${state} LIMIT #{start},#{count}")
    public List<LogFieldDTO> selectSPage(@Param("start") int start, @Param("count")int count,@Param("state")String state);
    @Insert("INSERT INTO `log_field_file_list` (`field_id`,`path_name`,`timeline`,`status`) " +
            "VALUES(#{fieldId},#{pathName},#{timeline},#{status}) ")
    public Integer addOneFile(LogFieldFileDTO logFieldFileDTO);
}
