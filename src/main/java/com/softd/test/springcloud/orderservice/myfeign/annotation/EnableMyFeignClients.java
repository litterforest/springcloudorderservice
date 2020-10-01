package com.softd.test.springcloud.orderservice.myfeign.annotation;

import com.softd.test.springcloud.orderservice.myfeign.FeignRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-10-01
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Import(FeignRegistrar.class)
public @interface EnableMyFeignClients {
    String basePackage() default "";
}
