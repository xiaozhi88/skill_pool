package com.xiaozhi.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangzy
 * @date 2020/7/29-16:16
 * @since v1.0
 */
@SpringBootApplication
@ComponentScan("com.xiaozhi.swagger.mapper")
public class SwaggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }
}
