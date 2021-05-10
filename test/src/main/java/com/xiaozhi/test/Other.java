package com.xiaozhi.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/26-14:19
 * @since v1.0
 */
public class Other {
    private static final Double DIVISOR = 10D;
    String str = new String("good");
    static int intTest = 100;
    static Integer intTest1 = 200;
    static final char[] ch = {'a', 'k', 'p'};
    char ar = 's';
    public static void main(String[] args) {
//        Other other = new Other();
//        other.changeValue(Other.ch, other.str, other.ar);
//        System.out.println(other.str + "aaa");
//        System.out.println(Other.ch);
//        System.out.println(other.ar);
////        BigDecimal num = BigDecimal.valueOf(156/DIVISOR);
////        System.out.println(num);
//        Son son = new Son();
//        Father father = new Father();
//        System.out.println(son.getStr());
//        Father father = new Father();
//        ArrayList<String> list = new ArrayList<>();
//        list.add("111");
//        list.add("222");
//        list.add("333");
//        changeList(list);
//        System.out.println(list);

//        Integer i1 = 100;
//        Integer i2 = 100;
//        Integer i3 = 200;
//        Integer i4 = 200;
//
//        System.out.println(i1==i2);
//        System.out.println(i3==i4);
        Double i1 = 100.0;
        Double i2 = 100.0;
        Double i3 = 200.0;
        Double i4 = 200.0;

        System.out.println(i1==i2);
        System.out.println(i3==i4);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(3);
        list.add(1);
        list.add(6);
        list.add(5);
        list.add(6);
        List<Integer> collect = list.stream()
                .filter(o -> o > 3)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        ArrayList<String> strings = new ArrayList<>();
        strings.add("123");
        strings.add("ppp");
        strings.add("hhh");
        strings.add("kkk");
        strings.add("ooo");
        changeList(strings);
        System.out.println("lpl");
        System.out.println("kok");

//        test();


//        int int1 = 1;
//        Integer int2 = 2;
//        changeFather(father, int1, int2, ch);
//        System.out.println(father.getInt1());
//        System.out.println(father.getInt2());
//        System.out.println(int1);
//        System.out.println(int2);
//        System.out.println(ch);

    }

    public static void changeList(List<String> list){
        String s = list.get(0);
        s = "haha";
        list.set(0, "第一个元素");
        list.add("新增元素");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("000");
        list = strings;
        System.out.println("集合: " + list);
    }

    public void changeValue(char[] ch, String str, char s){
        ch[0] = 'j';
        str = "bad";
        s = 't';
        System.out.println(Other.ch);
    }

    public static void changeFather(Father father, int intTest, Integer intTest1, char[] ch){
        char[] ch1 = {'l', 'p', 'r'};
        ch = ch1;
//        ch[0] = 'm';
        intTest = 1000;
        intTest1 = 2000;
        Father father1 = new Father(1);
        father.setInt1(11);
        father.setInt2(22);
        father = father1;

    }

    public static void test(){
//        System.out.println(1);
        test();
    }
}
