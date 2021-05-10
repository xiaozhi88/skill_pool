package com.xiaozhi.test;

import lombok.Data;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/27-9:53
 * @since v1.0
 */
@Data
public class Father {
    private static String str = "哈哈哈";
    public static String str1 = "哒哒哒";
    private int int1 = 1;
    private Integer int2 = 2;

    public Father(int i){
        this.int1 = i;
    }

    public static void main(String[] args) {
//        System.out.println(str);
    }

    public String getStr(){
        return str;
    }

}
