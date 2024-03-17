package com.test.used_car_application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private String title = "Used Car Application";
    private String version = "1.0";
    private String description = "This is a RESTful API for the Used Car Application.";
    private String name = "Your Name";
    private String email = "your.email@example.com";
    private String url = "https://www.used-car-app.com";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.test.used_car_application.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title(title)
                .version(version)
                .description(description)
                .contact(new Contact().name(name).url(url).email(email)));
    }
}

