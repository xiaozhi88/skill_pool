package com.money.rmb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.money.rmb.entity.MyEnum;
import com.money.rmb.mapper.MyEnumMapper;
import com.money.rmb.service.MyEnumService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述...
 *
 * @author zhangzy
 * @date 2022/4/12 11:25
 * Version 1.0
 */
@Service
public class MyEnumServiceImpl extends ServiceImpl<MyEnumMapper, MyEnum> implements MyEnumService {

    @Override
    public List<MyEnum> selectList() {
        return this.lambdaQuery().list();
    }
}
