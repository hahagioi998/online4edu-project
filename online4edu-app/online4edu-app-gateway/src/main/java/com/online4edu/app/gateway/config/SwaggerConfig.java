package com.online4edu.app.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 配置
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/13 22:01
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        // 请求头验证(用于需要验证登录信息的借口)
        List<Parameter> parameters = new ArrayList<>();

        parameters.add(
                new ParameterBuilder()
                        .name("token")
                        .description("令牌")
                        .modelRef(new ModelRef(String.class.getSimpleName()))
                        .parameterType("header")
                        .required(false)
                        .build()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.online4edu.app.gateway.web"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .termsOfServiceUrl("edu.itumate.top")
                .title("网关服务")
                .contact(
                        new Contact("Shilinn", "edu.itumate.top", "mingrn97@qq.com")
                )
                .version("1.0")
                .build();
    }

}
