package com.arthur.common.enums;

import cn.hutool.core.lang.func.Func;
import lombok.Getter;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 数据脱敏枚举
 * @author Arthur
 */
@Getter
public enum DesensitizationEnum {

    // 姓名脱敏策略，第2位星号替换
    NICKNAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),

    // 密码脱敏策略
    PASSWORD(s -> "******"),

    // 手机号脱敏策略，保留前三位和后四位
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),

    // 邮箱脱敏策略，保留邮箱用户名第一个字符和@符号前后部分
    EMAIL(s -> s.replaceAll("(\\w)[^@]*(@\\w+\\.\\w+)", "$1****$2")),

    // 身份证号脱敏策略，保留前四位和后四位
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2"));

    private final Function<String, String> desensitization;

    // 构造函数，传入一个函数作为参数
    DesensitizationEnum(Function<String, String> desensitization) {
        // 将传入的函数赋值给成员变量
        this.desensitization = desensitization;
    }

    public Function<String, String> desensitization () {
        return this.desensitization;
    }
}
