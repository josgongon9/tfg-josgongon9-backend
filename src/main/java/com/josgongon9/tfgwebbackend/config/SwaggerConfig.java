package com.josgongon9.tfgwebbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Value("${swagger.api.generalData.tittle}")
    private String title;
    @Value("${swagger.api.generalData.description}")
    private String description;
    @Value("${swagger.api.generalData.versio}")
    private String version;
    @Value("${swagger.api.generalData.name}")
    private String name;
    @Value("${swagger.api.generalData.url}")
    private String url;
    @Value("${swagger.api.generalData.email}")
    private String email;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey())).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();

    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(title, description, version, " ", new Contact(name, url, email), "LICENSE", "LICENSE URL", Collections.emptyList());
    }


}
