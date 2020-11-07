package com.softd.test.springcloud.orderservice.controller;

import com.softd.test.springcloud.orderservice.service.ProductFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    @Autowired
    private ProductFeignClient productFeignClient;
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
        String productSingle = productFeignClient.getProductSingle();
        System.err.println(productSingle);
        return "success, port:" + serverPort + "，orderId：" + orderId;
    }

    @PostMapping("/postOrder")
    public String postOrder(@RequestBody Map<String, Object> order){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object credentials = authentication.getCredentials();
        System.out.println(credentials.getClass().getName()+":"+authentication.getCredentials().toString());
        Object principal = authentication.getPrincipal();
        if(principal instanceof User){
            User user = (User)principal;
            System.out.println("principal:"+user);
        }else{
            System.out.println(principal.getClass().getName()+":"+principal);
        }
        Object details = authentication.getDetails();
        System.out.println(details.getClass().getName()+":"+authentication.getDetails().toString());
        log.info("order param:" + order);
        return "success, port:" + serverPort;
    }

}
