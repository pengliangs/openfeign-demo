package com.github.pengliangs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author pengliang on 2018-08-10 11:16
 */
@SpringBootApplication
@EnableEurekaServer
public class FeignEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignEurekaApplication.class, args);
    }
}
