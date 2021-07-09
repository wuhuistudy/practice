package com.springbootexample.practice.web.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author wuhui
 * @date 2021年07月02日 下午 01:40
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    /**
     * 可定义多个拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 定义过滤拦截的url名称，拦截所有请求
        registry.addInterceptor(new MyHttpInterceptor()).addPathPatterns("/**");
        // registry.addInterceptor(其他拦截器).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
