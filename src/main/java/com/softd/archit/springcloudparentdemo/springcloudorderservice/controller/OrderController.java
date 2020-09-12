package com.softd.archit.springcloudparentdemo.springcloudorderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-06-28
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/getOrder/{orderId}")
    public String getOrder(@PathVariable String orderId){
        log.info(orderId);
//        try {
//            Random random = new Random();
//            int randInt = random.nextInt(1500);
//            log.info("当前休眠时间是：" + randInt);
////            Thread.sleep(randInt);
//        } catch (InterruptedException e) {
//            log.error("", e);
//        }
        return "success, port:" + serverPort + "，orderId：" + orderId;
    }

    @PostMapping("/postOrder")
    public String postOrder(@RequestBody Map<String, Object> order){
        log.info("order param:" + order);
        return "success, port:" + serverPort;
    }

}
