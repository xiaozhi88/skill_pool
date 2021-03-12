package com.xiaozhi.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangzy
 * @date 2021/1/18-21:32
 * @since v1.0
 */
public class SpringTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.getBeanFactory().registerSingleton("classB", new ClassB());

    }
}
