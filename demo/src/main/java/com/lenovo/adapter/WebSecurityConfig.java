package com.lenovo.adapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    @Bean
    public HelloInterceptor getHelloInterceptor(){
        return new HelloInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(getHelloInterceptor());
        registration.addPathPatterns("/**");
    }
}
