package com.xiaozhi.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author zhangzy
 * @date 2020/9/12-10:48
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Controller
public class ClassA implements InA, Cloneable {
    public String a = "成员变量a";
    private String b;

    public void funcA(){
        System.out.println("funcA");
        InA inA = new InA() {
            @Override
            public void fgh() {

            }
        };
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) {
        LocalDateTime from = LocalDateTime.of(2020, 10, 4,
                10, 20, 55);
        LocalDateTime to = LocalDateTime.of(2020, 10, 4,
                10, 20, 55);
        System.out.println(from.equals(to));

        HashMap<String, Integer> hashMap = new HashMap<>(2);
        hashMap.put("1", 1);
        System.out.println(hashMap.get("2"));

        System.out.println("0206".substring(0,2));

    }

    @Override
    public void fgh() {

    }

}
