package com.xiaozhi;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/17-21:01
 * @since v1.0
 */
public class ThreadTest implements Runnable {
    @Override
    public void run() {
        System.out.println("123");
    }
}
