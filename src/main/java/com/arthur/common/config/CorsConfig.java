package com.arthur.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 跨域配置类
 * @author Arthur
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // 创建一个CorsConfiguration对象
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许所有来源
        configuration.addAllowedOriginPattern("*");
        // 允许所有方法
        configuration.addAllowedMethod("*");
        // 允许所有头
        configuration.addAllowedHeader("*");
        // 允许发送Cookie
        configuration.setAllowCredentials(true);
        // 设置最大缓存时间
        configuration.setMaxAge(3600 * 24L);
        // 创建一个UrlBasedCorsConfigurationSource对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 注册CorsConfiguration对象
        source.registerCorsConfiguration("/**", configuration);
        // 返回CorsConfigurationSource对象
        return source;
    }

}
