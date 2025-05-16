package com.example.bhcbbackend.configurations.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.NonNull;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SwaggerConfigurationProperties.class})
class SwaggerConfiguration
{
    @Bean
    OpenAPI openAPI(
            @NonNull final SwaggerConfigurationProperties properties
    )
    {
        return new OpenAPI()
                .info(new Info()
                        .title(properties.getTitle())
                        .description(properties.getDescription())
                        .version(properties.getVersion())
                        .contact(
                                new Contact()
                                        .name(properties.getContactName())
                                        .url(properties.getContactUrl())
                                        .email(properties.getContactEmail()))
                );
//TODO: add security
//                .components(
//                        new Components().addSecuritySchemes(
//                                "basic-auth",
//                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")
//                        )
//                );
    }

    @Bean
    GroupedOpenApi version1Api()
    {
        return GroupedOpenApi.builder()
                .group("Version 1")
                .packagesToScan("com.example.bhcbbackend.api.rest")
                .build();
    }

    //TODO: add security
//    @Bean
//    GlobalOpenApiCustomizer customize()
//    {
//        return openApi ->
//        {
//            Map<String, Schema> schemas = openApi.getComponents().getSchemas();
//            openApi.getComponents().setSchemas(new TreeMap<>(schemas));
//        };
//    }
}
