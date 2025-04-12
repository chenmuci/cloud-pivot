package com.arthur.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 获取 IP 工具类
 * @author Arthur
 */
@Slf4j
public class IpUtil {

    // 常见代理服务器头信息
    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private static final String LOCAL_IP = "127.0.0.1";

    private static final String UNKNOWN = "unknown";

    /**
     * 获取客户端真实 IP 地址
     */
    public static String getIpAddr() {
        // 获取当前请求
        HttpServletRequest request = ServletUtil.getRequest();
        // 遍历IP_HEADERS数组，获取请求头中的IP地址
        String ip = Arrays.stream(IP_HEADERS)
                .map(request::getHeader)
                .filter(ipVal -> ipVal != null && !ipVal.isEmpty() && !UNKNOWN.equalsIgnoreCase(ipVal))
                .findFirst()
                .orElseGet(request::getRemoteAddr);

        // 如果IP地址为"0:0:0:0:0:0:0:1"，则返回本地IP地址
        return "0:0:0:0:0:0:0:1".equals(ip) ? LOCAL_IP : ip;
    }

}
