package com.softd.test.springcloud.orderservice.myfeign;

import com.softd.test.springcloud.orderservice.myfeign.annotation.FeignClient;
import com.softd.test.springcloud.orderservice.myfeign.annotation.FeignGet;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-10-01
 */
public class FeignClientFactory implements FactoryBean, BeanClassLoaderAware {
    private Class interfaceType;
    private ClassLoader classLoader;
    public FeignClientFactory() {}
    public FeignClientFactory(Class interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public Object getObject() throws Exception {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //拿到我们的注解
                FeignClient annotation = (FeignClient) interfaceType.getAnnotation(FeignClient.class);
                String baseUrl  = annotation.hostName();
                if(method.getAnnotation(FeignGet.class)!=null) {
                    FeignGet feginGet = method.getAnnotation(FeignGet.class);
                    String url = baseUrl+feginGet.requestUrl();
                    System.out.println("发起请求");
                    String result = new RestTemplate().getForObject(url,String.class,"");
                    return result;
                }
                throw new IllegalAccessException("不符合要求");
            }
        };
        Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{interfaceType}, invocationHandler);
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Class getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Class interfaceType) {
        this.interfaceType = interfaceType;
    }
}
