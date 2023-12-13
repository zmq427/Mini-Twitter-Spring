package com.mq.config;

import com.mq.controller.interceptors.DataInterceptor;
import com.mq.controller.interceptors.LoginVerifyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    LoginVerifyInterceptor loginVerifyInterceptor;

    @Autowired
    DataInterceptor dataInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginVerifyInterceptor())
                .excludePathPatterns("/api/user/login_auth")
                .excludePathPatterns("/api/user/register")
                .addPathPatterns("/**");

        registry.addInterceptor(dataInterceptor);
    }

    @Bean
    public LoginVerifyInterceptor getLoginVerifyInterceptor() {
        return new LoginVerifyInterceptor();
    }
}
