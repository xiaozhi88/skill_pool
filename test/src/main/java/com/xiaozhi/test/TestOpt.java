package com.xiaozhi.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangzy
 * @date 2020/9/11-12:52
 * @since v1.0
 */
public class TestOpt {
    public static void main(String[] args) {
        List<String> stationIds = new ArrayList<>();
        AtomicReference<String> a = new AtomicReference<>("");
        System.out.println(a);
    }
}
