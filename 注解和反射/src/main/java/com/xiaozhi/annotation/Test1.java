package com.xiaozhi.annotation;

import java.lang.annotation.*;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/22-20:25
 * @since v1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@Inherited
public @interface Test1 {

}


