package com.money.rmb.controller;

import com.money.rmb.entity.MyEnum;
import com.money.rmb.service.MyEnumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述...
 *
 * @author zhangzy
 * @date 2022/4/12 13:16
 * Version 1.0
 */
@RestController
@RequestMapping("/myEnum")
//@AllArgsConstructor
@Api(tags = {"枚举数据库表控制层"})
public class MyEnumController {
    @Autowired
    private MyEnumService myEnumService;

    @GetMapping("/selectList")
    @ApiOperation("获取所有数据")
    private List<MyEnum> selectList(){
        return myEnumService.selectList();
    }
}
