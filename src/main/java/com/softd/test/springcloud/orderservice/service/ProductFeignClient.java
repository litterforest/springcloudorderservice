package com.softd.test.springcloud.orderservice.service;

import com.softd.test.springcloud.orderservice.myfeign.annotation.FeignClient;
import com.softd.test.springcloud.orderservice.myfeign.annotation.FeignGet;
import org.springframework.stereotype.Component;

@Component
@FeignClient(hostName = "http://localhost:8090")
public interface ProductFeignClient {
    @FeignGet(requestUrl = "/product/getProductSingle?productId=123456")
    String getProductSingle();
}
