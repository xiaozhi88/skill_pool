package com.xiaozhi.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author zhangzy
 * @date 2020/12/13-11:10
 * @since v1.0
 */
public class CompareTest {
    public static void main(String[] args) {
        ArrayList<LocalDateTime> timeArrayList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        timeArrayList.add(now);
        timeArrayList.add(now.minusDays(1));
        timeArrayList.add(now.minusDays(6));
        timeArrayList.add(now.minusDays(3));
        timeArrayList.add(now.plusDays(1));
        timeArrayList.add(now.plusDays(6));
        timeArrayList.add(now.plusDays(3));
        Collections.sort(timeArrayList);
//        System.out.println(timeArrayList);

        ArrayList<Test> tests = new ArrayList<>();
        tests.add(new Test("1", now));
        tests.add(new Test("2", now.minusDays(1)));
        tests.add(new Test("3", now.minusDays(6)));
        tests.add(new Test("4", now.minusDays(3)));
        tests.add(new Test("5", now.plusDays(1)));
        tests.add(new Test("6", now.plusDays(6)));
        tests.add(new Test("7", now.plusDays(3)));
        tests.sort(Comparator.comparing(Test::getTime));
//        System.out.println(tests);


        HashMap<Integer, Test> sh = new HashMap<>(3);
        sh.put(1, new Test("1", now));
        sh.put(2, new Test("2", now.minusDays(1)));
        sh.put(3, new Test("3", now.minusDays(6)));
        sh.put(4, new Test("4", now.minusDays(3)));
        sh.put(5, new Test("5", now.plusDays(1)));
        sh.put(6, new Test("6", now.plusDays(6)));
        sh.put(7, new Test("7", now.plusDays(3)));
//        sh.put("1", 6);
//        sh.put("2", 5);
//        sh.put("3", 3);
//        sh.put("4", 10);
//        sh.put("5", 7);
//        sh.put("6", 2);
//        sh.put("7", 8);
//        TreeMap<Integer, Integer> th = new TreeMap<>(sh);
//        Map<Integer, Integer> collect = th.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

//        Set<Map.Entry<Integer, Integer>> entries = th.entrySet();
//        ArrayList<Map.Entry<Integer, Integer>> entries1 = new ArrayList<>(entries);
//        entries1.sort(Map.Entry.comparingByValue());
//        System.out.println(entries1);
//        System.out.println(th);

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static
    class Test{
        private String name;
        private LocalDateTime time;
    }
}
