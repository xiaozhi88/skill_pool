package com.xiaozhi.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangzy
 * @date 2020/7/19-15:59
 * @since v1.0
 */
@SpringBootApplication
@MapperScan("com.xiaozhi.mp.mapper")
public class mybatisplusApplication {
    public static void main(String[] args) {
        SpringApplication.run(mybatisplusApplication.class, args);
    }
}
