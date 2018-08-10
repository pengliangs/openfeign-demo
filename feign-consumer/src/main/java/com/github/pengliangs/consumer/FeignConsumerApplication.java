package com.github.pengliangs.consumer;

import com.github.pengliangs.consumer.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengliang on 2018-08-10 11:26
 */
@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignConsumerApplication {

    @Autowired
    private PrintService printService;

    @GetMapping("/getPrint")
    public String getPrint(String content) {
        return printService.getPrint(content);
    }

    @GetMapping("/postPrint")
    public String postPrint(String content) {
        return printService.postPrint(content);
    }

    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApplication.class, args);
    }
}
