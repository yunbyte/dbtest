package com.yunbyte.dbtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 项目启动类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
@MapperScan("com.yunbyte.dbtest.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class DbTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbTestApplication.class, args);
    }

}
