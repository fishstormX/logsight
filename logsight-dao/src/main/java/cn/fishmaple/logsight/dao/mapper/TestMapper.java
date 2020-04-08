package cn.fishmaple.logsight.dao.mapper;

import org.apache.ibatis.annotations.Select;
public interface TestMapper{
    @Select("SELECT 1")
    public int checkConnect();
}
