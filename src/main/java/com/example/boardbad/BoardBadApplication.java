package com.example.boardbad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.boardbad.mapper")
public class BoardBadApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardBadApplication.class, args);
    }
}
