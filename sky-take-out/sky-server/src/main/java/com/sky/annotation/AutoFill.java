package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ⾃定义注解，⽤于标识某个⽅法需要进⾏功能字段⾃动填充处理
 */
//ElementType.METHOD表⽰该注解只能⽤于⽅法上
@Target(ElementType.METHOD)
//RetentionPolicy.RUNTIME表⽰该注解在程序运⾏时仍然可⽤（可以通过反射读取）

@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //数据库操作类型：UPDATE INSERT
    OperationType value();
}
