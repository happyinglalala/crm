package com.zhangxueqiang.crm.config;

import com.zhangxueqiang.crm.Interceptor.NoLoginInterceptor;
import com.zhangxueqiang.crm.exceptions.NoLoginException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * ClassName: MvcConfig
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/8 - 20:43
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public NoLoginInterceptor noLoginInterceptor(){
        return new NoLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/images/**","/js/**","/lib/**","/index","/user/login");

    }
}
