package com.github.pengliangs.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengliang on 2018-08-10 11:23
 */
@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class FeignProviderApplication {

    @GetMapping("/getPrint")
    public String getPrint(@RequestParam("content") String content) {
        return "getPrint输入内容：".concat(content);
    }

    @PostMapping("/postPrint")
    public String postPrint(@RequestParam("content") String content) {
        return "postPrint输入内容：".concat(content);
    }


    public static void main(String[] args) {
        SpringApplication.run(FeignProviderApplication.class, args);
    }
}
