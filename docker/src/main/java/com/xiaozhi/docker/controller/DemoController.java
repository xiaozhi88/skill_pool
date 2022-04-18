package com.xiaozhi.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述...
 *
 * @author zhangzy
 * @date 2022/4/14 17:09
 * Version 1.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping("/test")
    public String test(){
        return "docker学习测试接口!!!";
    }
}
