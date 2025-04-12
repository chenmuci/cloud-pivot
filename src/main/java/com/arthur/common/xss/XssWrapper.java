package com.arthur.common.xss;

import com.arthur.common.utils.XssUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;

/**
 * Xss 攻击过滤
 * @author Arthur
 */
public class XssWrapper extends HttpServletRequestWrapper {

    public XssWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 对数组参数进行特殊字符过滤
     */
    @Override
    public String[] getParameterValues(String name) {
        // 获取参数值
        String[] values = super.getParameterValues(name);
        // 如果参数值为空，则返回null
        if (values == null) {
            return null;
        }
        // 获取参数值的长度
        int count = values.length;
        // 创建一个新的字符串数组，用于存储过滤后的参数值
        String[] encodedValues = new String[count];
        // 遍历参数值数组
        for (int i = 0; i < count; i++) {
            // 对参数值进行特殊字符过滤
            encodedValues[i] = cleanXss(values[i]);
        }
        // 返回过滤后的参数值数组
        return encodedValues;
    }

    /**
     * 对参数中特殊字符进行过滤
     */
    @Override
    public String getParameter(String name) {
        // 调用父类的方法获取参数值
        String value = super.getParameter(name);
        // 如果参数值为空，则直接返回
        if (StringUtils.isBlank(value)) {
            return value;
        }
        // 对参数值进行特殊字符过滤
        return cleanXss(value);
    }

    /**
     * 获取attribute,特殊字符过滤
     */
    @Override
    public Object getAttribute(String name) {
        // 调用父类的方法获取attribute
        Object value = super.getAttribute(name);
        // 判断value是否为String类型且不为空
        if (value instanceof String && StringUtils.isNotBlank((String) value)) {
            // 如果是，则调用cleanXss方法进行特殊字符过滤
            return cleanXss((String) value);
        }
        // 否则返回value
        return value;
    }

    /**
     * 对请求头部进行特殊字符过滤
     */
    @Override
    public String getHeader(String name) {
        // 获取请求头部的值
        String value = super.getHeader(name);
        // 如果值为空，则直接返回
        if (StringUtils.isBlank(value)) {
            return value;
        }
        // 对值进行特殊字符过滤
        return cleanXss(value);
    }

    /**
     * 清除XSS攻击
     */
    private String cleanXss(String value) {
        // 调用XssUtil类的clean方法，清除XSS攻击
        return XssUtil.clean(value);
    }
}
