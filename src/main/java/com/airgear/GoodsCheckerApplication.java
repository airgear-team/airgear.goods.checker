package com.airgear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GoodsCheckerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsCheckerApplication.class, args);
    }
}
