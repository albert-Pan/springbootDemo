package com.pw.example.demo4;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.pw.example.demo4.dao")
public class Demo4Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo4Application.class, args);
    }

}
