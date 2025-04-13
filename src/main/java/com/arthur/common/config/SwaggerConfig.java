package com.arthur.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 接口文档配置
 * @author Arthur
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi testApi() {
        return createRestApi("default", "com.arthur.test");
    }

    @Bean
    public GroupedOpenApi systemApi() {
        return createRestApi("系统管理", "com.arthur.system");
    }

    public GroupedOpenApi createRestApi(String groupName, String packagesToScan) {
        return GroupedOpenApi.builder().group(groupName).packagesToScan(packagesToScan).build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("云枢接口文档")
                        .description("云端枢转，智控未来。 From Cloud to Control, Everything Connected.")
                        .version("v1.0.0")
                        .contact(new Contact().name("Arthur").url("https://www.cloudpivot.com").email("arthur@cloudpivot.com"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
