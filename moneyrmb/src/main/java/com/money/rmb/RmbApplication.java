package com.money.rmb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.money.*"})
@EnableDiscoveryClient
//@NacosPropertySource(dataId = "make-money-rmb", autoRefreshed = true)
public class RmbApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmbApplication.class, args);
    }

}
