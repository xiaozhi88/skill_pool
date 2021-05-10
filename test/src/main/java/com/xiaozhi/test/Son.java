package com.xiaozhi.test;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/27-9:53
 * @since v1.0
 */
public class Son extends Father{
    String a;

    public Son(){
        super(1);
    }
    public static void main(String[] args) {
        String s1 = "哈哈呵";
        String s2 = new String("哈哈呵");
        String s3 = "哈哈" + "呵";
        System.out.println(s1 ==s2);
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s2));
        System.out.println(s3==s1.intern());
    }
}
