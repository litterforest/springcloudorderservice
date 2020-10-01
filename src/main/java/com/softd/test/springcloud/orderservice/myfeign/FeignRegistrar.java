package com.softd.test.springcloud.orderservice.myfeign;

import com.softd.test.springcloud.orderservice.myfeign.annotation.EnableMyFeignClients;
import com.softd.test.springcloud.orderservice.myfeign.annotation.FeignClient;
import com.softd.test.springcloud.orderservice.myfeign.annotation.FeignGet;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

/**
 * 扫描feign client相关注解，创建代理
 *
 * @author cobee
 * @since 2020-10-01
 */
public class FeignRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware,
        BeanClassLoaderAware {
    private Environment environment;
    private ResourceLoader resourceLoader;
    private ClassLoader classLoader;
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 扫描包路径
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
                false, this.environment) {
            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) { // 扫描独立类
                    if (!beanDefinition.getMetadata().isAnnotation()) { // 排除掉注解类
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
        scanner.setResourceLoader(resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(
                FeignClient.class);
        String basePackage = "";
        Map<String, Object> attrs = importingClassMetadata
                .getAnnotationAttributes(EnableMyFeignClients.class.getName());
        if (attrs != null && !attrs.isEmpty()) {
            basePackage = (String) attrs.get("basePackage");
        }
        scanner.addIncludeFilter(annotationTypeFilter);
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
        for (BeanDefinition bd : candidateComponents) {
            AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) bd;
            // 只处理接口类型
            if (annotatedBeanDefinition.getMetadata().isInterface()) {
                registerBeanProxy(annotatedBeanDefinition, registry);
            }
        }
    }

    private void registerBeanProxy(AnnotatedBeanDefinition annotatedBeanDefinition, BeanDefinitionRegistry registry) {
        try {
            Class<?> clazz = ClassUtils.forName(annotatedBeanDefinition.getBeanClassName(), classLoader);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinitionBuilder.getRawBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
            definition.setBeanClass(FeignClientFactory.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            registry.registerBeanDefinition(clazz.getSimpleName(), definition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
