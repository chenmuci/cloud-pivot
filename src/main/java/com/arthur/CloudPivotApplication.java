package com.arthur;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author Arthur
 */
@Slf4j
@SpringBootApplication
public class CloudPivotApplication {

    public static void main(String[] args) {
        // 运行Spring Boot应用程序，并获取可配置的环境
        ConfigurableEnvironment env = SpringApplication.run(CloudPivotApplication.class, args).getEnvironment();
        // 打印启动成功的日志信息
        log.info("""

                    云枢启动成功！
                    Local URL： http://localhost:{}
                    API DOCS： http://localhost:{}{}/swagger-ui/index.html
                """,
                // 获取服务器端口
                env.getProperty("server.port"),
                // 获取服务器端口
                env.getProperty("server.port"),
                // 获取服务器上下文路径
                env.getProperty("server.servlet.context-path"));
    }

}
