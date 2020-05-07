package cn.fishmaple.logsight.dao.mapper;

import cn.fishmaple.logsight.dao.dto.LogFieldTreeDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogFieldTreeMapper {
    @Insert("<script> INSERT INTO log_field_tree(field_id,level,parent_id,last_scan,name) "
            + "VALUES <foreach collection=\"list\" item=\"logfieldTree\" index=\"index\" separator=\",\">" +
            "(#{logfieldTree.fieldId},#{logfieldTree.level},#{logfieldTree.parentId}," +
            "#{logfieldTree.lastScan},#{logfieldTree.name})</foreach></script>")
    int add(@Param("list") List<LogFieldTreeDTO> list);
}
