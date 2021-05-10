package com.xiaozhi.test;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/4/21-16:56
 * @since v1.0
 */
public class Test1 {
    public static void main(String[] args) {
        /*HashMap<String, Integer> map = new HashMap<>(2);
        map.put("A", 3);
        map.put("B", 1);
        map.put("C", 4);
        map.put("D", 2);
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(s -> linkedHashMap.put(s.getKey(), s.getValue()));
        System.out.println(linkedHashMap);
        Set<String> set = linkedHashMap.keySet();
        for (String s : set) {
            System.out.println(s);
        }
        ArrayList<String> strings = new ArrayList<>(set);
        System.out.println(strings);*/
        List<Object> objects = Collections.emptyList();
        ArrayList<Object> objects1 = new ArrayList<>();
        objects1.add(null);
        boolean empty = CollectionUtil.isEmpty(objects1);
        boolean empty1 = CollectionUtils.isEmpty(objects1);
        System.out.println(objects1);
        System.out.println(empty);
        System.out.println(empty1);
    }
}
