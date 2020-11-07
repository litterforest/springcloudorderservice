package com.softd.test.springcloud.orderservice;

import com.softd.test.springcloud.orderservice.myfeign.annotation.EnableMyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
@EnableResourceServer
@EnableMyFeignClients(basePackage = "com.softd.test.springcloud.orderservice")
//@EnableOAuth2Sso
//@EnableOAuth2Client
public class SpringCloudOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudOrderServiceApplication.class, args);
    }

}
