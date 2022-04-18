package com.xiaozhi.cglib;

/**
 * 描述...
 *
 * @author zhangzy
 * @date 2022/3/25 9:34
 * Version 1.0
 */
public class Dog {
    final public void run(String name) {
        System.out.println("狗"+name+"----run");
    }

    public void eat() {
        System.out.println("狗----eat");
    }
}
