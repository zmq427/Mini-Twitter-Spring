package com.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MiniTwitterApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniTwitterApplication.class, args);
        System.out.println("项目启动成功");
    }
}
