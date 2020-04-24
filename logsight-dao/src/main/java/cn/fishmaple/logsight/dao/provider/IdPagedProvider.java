package cn.fishmaple.logsight.dao.provider;

import cn.fishmaple.logsight.dao.object.IdPagedStat;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

public class IdPagedProvider {
    public String fieldDistinctFilePaged(IdPagedStat idPagedStat){
        SQL sql =new SQL();
        sql.SELECT_DISTINCT("path_name pathName").FROM("log_field_file");
        sql.WHERE("id > "+ idPagedStat.getStartId());
        if(null!= idPagedStat.getEndId()){
            sql.WHERE("id < "+ idPagedStat.getEndId());
        }
        return sql.toString();
    }

    public static void main(String args[]){
        IdPagedStat idPagedStat=new IdPagedStat();
        idPagedStat.setFields("");
        idPagedStat.setStartId(0L);
        idPagedStat.setEndId(5L);
        System.out.println(new IdPagedProvider().fieldDistinctFilePaged(idPagedStat));
    }
}
