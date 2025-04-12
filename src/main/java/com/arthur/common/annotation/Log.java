package com.arthur.common.annotation;

import com.arthur.common.enums.BusinessEnum;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 * @author Arthur
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    // 所属模块
    String module() default "";

    // 描述
    String desc() default "";

    // 业务类型
    BusinessEnum type() default BusinessEnum.OTHER;

}
