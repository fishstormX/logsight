package cn.fishmaple.logsight.dao.connect;


import cn.fishmaple.logsight.config.ConfigLoader;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

//表示这个类为一个配置类
@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "cn.fishmaple.logsight.dao.mapper", sqlSessionFactoryRef = "h2SqlSessionFactory")
public class H2DataSourceConf {
    @Autowired
    private ConfigLoader configLoader;
    private Class<? extends DataSource> dataSourceType = DruidDataSource.class;
    @Bean(name = "h2DataSource")
    @Primary
    public DataSource getDateSource(){
        try {
            dataSourceType = (Class<? extends DataSource>) Class.forName("com.alibaba.druid.pool.DruidDataSource");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String driverClassName = configLoader.getConfig("datasource.h2.driver-class-name","org.h2.Driver");
        String url = configLoader.getConfig("datasource.h2.url")+";CASE_INSENSITIVE_IDENTIFIERS=TRUE;database_to_upper=false;IGNORECASE=TRUE;AUTO_SERVER=TRUE;MODE=MySQL;AUTO_RECONNECT=TRUE";
        String username = configLoader.getConfig("datasource.h2.username","sa");
        String psw = configLoader.getConfig("datasource.h2.password","sa");
        String filter = configLoader.getConfig("datasource.h2.filters","stat");
        String validationQuery = configLoader.getConfig("datasource.h2.validationQuery","SELECT 1");
        Integer timeout = configLoader.getIntConfig("datasource.h2.timeout",30);
        Integer initialSize = configLoader.getIntConfig("datasource.h2.initialSize",10);
        Integer minIdle = configLoader.getIntConfig("datasource.h2.minIdle",30);
        Integer maxActive = configLoader.getIntConfig("datasource.h2.macActive",100);
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(psw);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setQueryTimeout(timeout);
        druidDataSource.setValidationQueryTimeout(timeout);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        try {
            druidDataSource.setFilters(filter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        try {
            InputStream inputStream = getClass().getResourceAsStream("/db/logsight.sql");
            byte[] tmp = new byte[inputStream.available()];
            inputStream.read(tmp);
            String s = new String(tmp,"UTF-8");
            System.out.println(s);
            druidDataSource.getConnection().createStatement().execute(s);
        } catch (SQLException|IOException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }
    @Bean(name = "h2SqlSessionFactory")
    @Primary
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("h2DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        return bean.getObject();
    }
    @Bean("h2SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate test1sqlsessiontemplate(
            @Qualifier("h2SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
