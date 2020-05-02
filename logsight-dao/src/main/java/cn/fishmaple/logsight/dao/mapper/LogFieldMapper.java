package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface LogFieldMapper {
    @Select("SELECT id ,status,`file_count` fileCount,`size`,`path`,`remarks` FROM `log_field` WHERE `id` = #{id}" )
    public LogFieldDTO getFieldById(String id);
    @Insert("INSERT INTO `log_field` (`path`,`create_time`,`status`,`remarks`) VALUES(#{path},#{createTime},#{status},#{remarks}) ")
    public Integer addOne(LogFieldDTO logFieldDTO);
    @Update("UPDATE `log_field` SET timeline = #{timeline},file_count = #{fileCount},`status` = #{status},`size` = #{size} WHERE `id` = #{id}")
    public Integer scanUpdate(LogFieldDTO logFieldDTO);
    @Update("UPDATE `log_field` SET create_time = #{createTime},path = #{path},`status` =#{status},file_count = null" +
            ",`remarks` = #{remarks} ,size = null WHERE `id` = #{id}")
    public Integer update(LogFieldDTO logFieldDTO);
    @Select("SELECT `id`,`path`,`status` FROM `log_field` WHERE `status` = 0")
    public List<LogFieldDTO> selectUnScannedField();
    @Select("SELECT `id`,`path`,`status` FROM `log_field` WHERE `status` != 2")
    public List<LogFieldDTO> selectUnClosedField();
    @Select("SELECT COUNT(*) FROM `log_field`")
    public Integer count();
    @Select("SELECT COUNT(*) FROM `log_field` WHERE  `status` = 1")
    public Integer availableCount();
    @Select("SELECT `id`,`path`,`timeline`,`status`,`create_time` createTime,`file_count` fileCount,`size`,`remarks` FROM `log_field` LIMIT #{start},#{count}")
    public List<LogFieldDTO> selectAPage(@Param("start") int start, @Param("count")int count);
    @Select("SELECT `id`,`path`,`timeline`,`status`,`create_time` createTime,`file_count` fileCount,`size`,`remarks`  FROM `log_field` " +
            "${state} LIMIT #{start},#{count}")
    public List<LogFieldDTO> selectSPage(@Param("start") int start, @Param("count")int count,@Param("state")String state);
    @Delete("DELETE FROM `log_field`  WHERE `id` = #{id} ")
    public Integer delete(Integer id);

}
