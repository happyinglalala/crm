package com.zhangxueqiang.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * ClassName: Starter
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/7 - 20:42
 */
@SpringBootApplication
@MapperScan("com.zhangxueqiang.crm.dao")
public class Starter extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }

//    设置web项目的启动入口


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        设定启动入口
        return builder.sources(Starter.class);
    }
}
