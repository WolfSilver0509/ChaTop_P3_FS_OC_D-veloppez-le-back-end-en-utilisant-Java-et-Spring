package com.p3_oc_fs.chatop.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Chatop API")
                        .description("Chatop API documentation Pour le P3 DEV FULL STACK OPENCLASSROOMS")
                        .version("1.0.0"))
                .components(new Components().addSecuritySchemes("JWT", securityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("JWT"));
    }

    @Bean
    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("JWT")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}