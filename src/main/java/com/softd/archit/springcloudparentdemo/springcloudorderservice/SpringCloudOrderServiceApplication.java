package com.softd.archit.springcloudparentdemo.springcloudorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudOrderServiceApplication.class, args);
    }

}