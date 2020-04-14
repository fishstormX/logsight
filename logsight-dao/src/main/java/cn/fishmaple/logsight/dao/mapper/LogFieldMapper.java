package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LogFieldMapper {
    @Select("SELECT 1")
    public int checkConnect();
    @Insert("INSERT INTO `log_field` (`path`,`create_time`,`status`) VALUES(#{path},#{createTime},#{status}) ")
    public int addOne(LogFieldDTO logFieldDTO);
    @Update("UPDATE `log_field` SET timeline = #{timeline},file_count = #{fileCount}")
    public int update(LogFieldDTO logFieldDTO);
    @Select("SELECT `id`,`path`,`timeline`,`status`,`create_time` createTime,`file_count` fileCount FROM `log_field` ORDER BY ${orderBy} LIMIT #{start},#{count}")
    public List<LogFieldDTO> selectAPage(@Param("start") int start, @Param("count")int count,@Param("orderBy")String orderBy);

}
