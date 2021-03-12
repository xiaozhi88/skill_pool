package com.xiaozhi.test;

/**
 * @author zhangzy
 * @date 2020/9/12-10:47
 * @since v1.0
 */
public interface InA {
    static void testStaticFunction(){

    }
    default void foo(){
        System.out.println("ggg");
    }
    void fgh();
}
