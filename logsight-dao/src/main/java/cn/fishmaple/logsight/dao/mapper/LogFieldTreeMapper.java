package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldTreeDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface LogFieldTreeMapper {
    @Insert("<script> INSERT INTO log_field_tree(field_id,level,parent_id,last_scan,name) "
            + "VALUES <foreach collection=\"list\" item=\"logfieldTree\" index=\"index\" separator=\",\">" +
            "(#{logfieldTree.fieldId},#{logfieldTree.level},#{logfieldTree.parentId}," +
            "#{logfieldTree.lastScan},#{logfieldTree.name})</foreach></script>")
    int batchAdd(@Param("list") List<LogFieldTreeDTO> list);

    @Insert("INSERT INTO log_field_tree(field_id,level,parent_id,last_scan,name,path,last_flag) "
            + "VALUES (#{fieldId},#{level},#{parentId},#{lastScan},#{name},#{path},#{lastFlag})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer add(LogFieldTreeDTO logFieldTreeDTO);

    @Select("SELECT `id` FROM log_field_tree where field_id = #{fieldId} and `path` = #{path}")
    Long selectId(@Param("fieldId")Integer fieldId,@Param("path")String path);

    @Select("SELECT `id` FROM log_field_tree where field_id = #{fieldId} and `path` = #{path}")
    Long select(@Param("fieldId")Integer fieldId, @Param("path")String path);

    @Select("SELECT id,field_id fieldId,level,parent_id parentId,name,path FROM log_field_tree where field_id = #{fieldId} and `parent_id` = #{parentId}")
    List<LogFieldTreeDTO> selectDetail(@Param("fieldId")Integer fieldId, @Param("parentId")Long parentId);
    @Select("SELECT id,field_id fieldId,level,parent_id parentId,name,path FROM log_field_tree where field_id = #{fieldId} and `parent_id` = #{parentId} order by id limit ${limit}")
    List<LogFieldTreeDTO> selectDetailLimit(@Param("fieldId")Integer fieldId, @Param("parentId")Long parentId,@Param("limit")Integer limit);
}
