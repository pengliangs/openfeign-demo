package com.github.pengliangs.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author pengliang on 2018-08-10 11:32
 */
@FeignClient("feign-provider")
public interface PrintService {

    @PostMapping(value = "/postPrint")
    String postPrint(@RequestParam("content") String content);

    @GetMapping(value = "/getPrint")
    String getPrint(@RequestParam("content")String content);
}
