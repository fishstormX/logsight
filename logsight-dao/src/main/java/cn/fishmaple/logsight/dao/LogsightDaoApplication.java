package cn.fishmaple.logsight.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication(scanBasePackages = {"cn.fishmaple.logsight"})
public class LogsightDaoApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LogsightDaoApplication.class, args);
    }

}
