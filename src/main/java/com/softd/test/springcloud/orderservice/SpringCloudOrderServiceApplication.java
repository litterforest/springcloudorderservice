package com.softd.test.springcloud.orderservice;

import com.softd.test.springcloud.orderservice.myfeign.annotation.EnableMyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
@EnableMyFeignClients(basePackage = "com.softd.test.springcloud.orderservice")
public class SpringCloudOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudOrderServiceApplication.class, args);
    }

}
