package com.zhao;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.zhao.mapper")
@EnableAsync
public class SpringbootMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootMain.class, args);
    }
}
