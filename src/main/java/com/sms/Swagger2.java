package com.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jared
 * @date 2021/6/5 9:49
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    // 扫描全部包
    final static String basePackage2 = "com.sms";

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage2))
                .paths(PathSelectors.any())
                .build()
                // 添加登录认证
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .contact(new Contact("Jared","www.baidu.com","982587532@qq.com"))
                .title("海大体育管理系统")
                .description("海大体育管理系统的接口测试")
                .version("1.0")
                .license("The Apache License, Version 1.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

    /**--------------          配置多包扫描         ----------------------------------------*/

    /**--------------  springSecurity整合swagger2 配置全局authorization ---------------------  */
    private List<SecurityScheme> securitySchemes() {
        // 设置请求头信息
        List<SecurityScheme> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        // 设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath());
        return result;
    }

    private SecurityContext getContextByPath() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("/brand/.*"))
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }
}
