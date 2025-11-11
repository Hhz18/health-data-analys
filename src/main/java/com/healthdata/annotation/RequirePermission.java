package com.healthdata.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//自定义权限校验注解
public @interface RequirePermission {
    String resource(); // 资源（如"patient_info"）
    String action(); // 操作（如"view"）
}