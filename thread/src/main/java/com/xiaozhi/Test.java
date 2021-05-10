package com.xiaozhi;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/17-22:16
 * @since v1.0
 */
public class Test {
    private static final AtomicInteger COUNT = new AtomicInteger();
    public static void main(String[] args) {
        int i = COUNT.incrementAndGet();
        int j = COUNT.incrementAndGet();
        System.out.println(i);
        System.out.println(j);

//        for (;;){
//            System.out.println(111);
//        }

    }
}
