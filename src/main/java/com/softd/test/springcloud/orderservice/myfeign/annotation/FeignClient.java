package com.softd.test.springcloud.orderservice.myfeign.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface FeignClient {
    /**
     * 主机地址或域名
     *
     * @return
     */
    String hostName() default "";
}
