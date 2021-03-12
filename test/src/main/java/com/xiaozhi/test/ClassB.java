package com.xiaozhi.test;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangzy
 * @date 2020/9/12-10:59
 * @since v1.0
 */
@Repository
public class ClassB implements InA {
    ClassA classA;
    public void c(){
        foo();
    }
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    ReadWriteLock l = new ReentrantReadWriteLock();
    public static void main(String[] args) throws CloneNotSupportedException {
//        List<String> list = Stream.of("a", "b", "c", "d", "e", "f").collect(Collectors.toList());
//        Iterator<String> iterator = list.iterator();
//        System.out.println(list);
//        Integer a = 123;
//        Integer b = 123;
//        System.out.println(a.toString());
//        ClassA classA = new ClassA("000", "123");
//        Object clone = classA.clone();
//        System.out.println(clone);
        double dou = 3.1487426;
        String douStr = String.format("%.2f", dou);
        System.out.println(douStr);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        Integer integer = stringIntegerHashMap.putIfAbsent("1", 2);
        Integer integer1 = stringIntegerHashMap.putIfAbsent("1", 3);
        if (!ObjectUtils.isEmpty(integer1)){
            stringIntegerHashMap.put("1", integer1 + 3);
        }

        Integer integer2 = stringIntegerHashMap.get("666");
        System.out.println(integer);
        System.out.println(integer1);
        System.out.println(stringIntegerHashMap);
        System.out.println(integer2);

    }

    @Override
    public void fgh() {

    }
}
