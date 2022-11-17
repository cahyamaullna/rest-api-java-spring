/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freddxant.config;

import com.google.common.base.Predicate;
import static com.google.common.base.Predicates.not;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.service.contexts.SecurityContext;

/**
 *
 * @author freddxant
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {
    
    @Bean
    public Docket api() {
        SecurityReference securityReference = SecurityReference.builder()
                        .reference("basicAuth")
                        .scopes(new AuthorizationScope[0])
                        .build();

        ArrayList<SecurityReference> reference = new ArrayList<>(1);
        reference.add(securityReference);

        ArrayList<SecurityContext> securityContexts = new ArrayList<>(1);
        securityContexts.add(SecurityContext.builder().securityReferences(reference).build());

        ArrayList<SecurityScheme> auth = new ArrayList<>(1);
        auth.add(new BasicAuth("basicAuth"));
                
        return new Docket(DocumentationType.SWAGGER_2)
            .securitySchemes(auth)
            .securityContexts(securityContexts)
            .select()
            .apis(RequestHandlerSelectors
                .basePackage("com.freddxant.controller"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiInfo());
    }
    
    private Predicate<String> exceptErrorPaths() {
        return not(PathSelectors.regex("/error"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("REST API EMPLOYEE")
                .description("REST API EMPLOYEE")
                .termsOfServiceUrl("freddxant.com")
                .licenseUrl("freddxant.com")
                .contact(new Contact("Freddxant", "", "mail@freddxant.com"))
                .license("Freddxant License")
                .version("0.0.1")
                .build();
    }
    
}
