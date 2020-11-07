package com.softd.test.springcloud.orderservice.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 配置资源服务器
 */
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String DEMO_RESOURCE_ID = "order";

    /*@Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //设置资源ID
        resources.resourceId(DEMO_RESOURCE_ID)
                //标志，指示仅允许对这些资源进行基于令牌的身份验证。
                .stateless(true);
    }*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //配置order访问控制，必须认证过后才可以访问
        http.cors().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.authorizeRequests().antMatchers("/order/**").authenticated();
//        http.authorizeRequests() .antMatchers("/oauth/**","/product/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
    }
}