package com.xiaozhi.test;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author zhangzy
 * @date 2021/1/18-21:45
 * @since v1.0
 */
@Component
public class FactoryBeanTest implements FactoryBean<Object> {

    @Override
    public Object getObject() throws Exception {
        return new ClassB();
    }

    @Override
    public Class<?> getObjectType() {
        return ClassB.class;
    }
}
