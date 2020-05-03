package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import cn.fishmaple.logsight.dao.dto.LogGroupDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogGroupMapper {
    @Select("SELECT id ,name,`field_count` fieldCount,`remarks`,`fieldIds` FROM `log_group` WHERE `id` = #{id}" )
    public LogGroupDTO getGroupById(Integer id);
    @Insert("INSERT INTO `log_group` (`name`,`create_time`,`status`,`remarks`,`fieldIds`,`field_count`,`timeline`) " +
            "VALUES(#{name},#{createTime},#{status},#{remarks},#{fieldIds},#{fieldCount},#{createTime}) ")
    public Integer addOne(LogGroupDTO logGroupDTO);
    @Update("UPDATE `log_group` SET timeline = #{timeline},name = #{name},field_count = #{fieldCount}" +
            ",`remarks` = #{remarks},`fieldIds` = #{fieldIds} WHERE `id` = #{id}")
    public Integer update(LogGroupDTO logGroupDTO);
    @Select("SELECT COUNT(*) FROM `log_group`")
    public Integer count();
    @Select("SELECT `id`,`name`,`timeline`,`status`,`create_time` createTime,`field_count` fieldCount,`fieldIds`,`remarks` FROM `log_group` LIMIT #{start},#{count}")
    public List<LogGroupDTO> selectAPage(@Param("start") int start, @Param("count") int count);
    @Select("SELECT `id`,`name`,`timeline`,`status`,`create_time` createTime,`field_count` fieldCount,`fieldIds`,`remarks`  FROM `log_group` " +
            "${state} LIMIT #{start},#{count}")
    public List<LogGroupDTO> selectSPage(@Param("start") int start, @Param("count") int count, @Param("state") String state);
    @Delete("DELETE FROM `log_group`  WHERE `id` = #{id} ")
    public Integer delete(Integer id);

}
