package com.xiaozhi.test;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zhangzy
 * @date 2020/9/12-15:52
 * @since v1.0
 */
@Service
public class ClassC {
    static {
        System.out.println("abc");
    }

    {
        System.out.println("普通语句块");
    }

    class InnerClass {

    }

    static class StaticInnerClass{

    }

    public void func(){
        System.out.println("333");
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        ClassC classC = new ClassC(){
            @Override
            public void func(){
                System.out.println("666");
            }

            public void t1(){
                System.out.println("999");
            }
        };
        classC.func();
        ClassA classA = new ClassA();
        Class<?> aClass = Class.forName("com.xiaozhi.test.ClassA");
        System.out.println(aClass);
        System.out.println(classA.getClass());
        Field[] fields = aClass.getDeclaredFields();
        Class<?> type = fields[0].getDeclaringClass();
        System.out.println(type);
        Method declaredMethod = aClass.getDeclaredMethod("funcA");
        System.out.println(declaredMethod.getDeclaringClass());
        Class<Data> dataClass = Data.class;
        for (Method method : dataClass.getDeclaredMethods()) {
            System.out.println("Data注解方法:" + method.getName());
        }
        for (Annotation annotation : dataClass.getAnnotations()) {
            System.out.println("Data注解的注解:" + annotation.toString());
        }
        for (Method staticConstructor : dataClass.getMethod("staticConstructor").getDeclaringClass().getMethods()) {
            System.out.println(staticConstructor.getName());
        }
        System.out.println();
        byte[] bytes = "hg".getBytes();
        String s = new String(bytes);
        System.out.println();
        System.out.println(s);

        StructTestA structTestA = new StructTestA();

    }
}
