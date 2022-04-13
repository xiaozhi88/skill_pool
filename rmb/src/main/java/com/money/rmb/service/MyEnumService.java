package com.money.rmb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.money.rmb.entity.MyEnum;

import java.util.List;

/**
 *  
 *  描述...
 *  
 *  @author zhangzy
 *  @date 2022/4/12 11:24
 *  Version 1.0
 */
public interface MyEnumService extends IService<MyEnum> {

    List<MyEnum> selectList();

}
