package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LogFieldMapper {
    @Select("SELECT 1")
    public int checkConnect();
    @Insert("INSERT INTO `log_field` (`path`,`timeline`,`status` ) VALUES(#{path},#{timeline},#{status}) ")
    public int addOne(LogFieldDTO logFieldDTO);
    @Select("SELECT `id`,`path`,`timeline`,`status` FROM `log_field` ORDER BY `id` LIMIT #{start},#{count}")
    public List<LogFieldDTO> selectAPage(@Param("start") int start, @Param("count")int count);

}
