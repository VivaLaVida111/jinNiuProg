package com.example.jinniuprog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages="com.example.jinniuprog.mapper")

public class JinNiuProgApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinNiuProgApplication.class, args);
    }

}
