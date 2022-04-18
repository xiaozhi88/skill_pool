package com.xiaozhi;

import java.util.concurrent.Callable;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/17-21:01
 * @since v1.0
 */
public class ThreadTest implements Callable {
//    @Override
//    public void run() {
//        System.out.println("123");
//    }

    @Override
    public Object call() throws Exception {
        return "callable返回值";
    }
}
