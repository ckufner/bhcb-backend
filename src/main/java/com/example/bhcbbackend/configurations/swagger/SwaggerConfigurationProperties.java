package com.example.bhcbbackend.configurations.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("com.example.bhcbbackend.swagger.api.information")
class SwaggerConfigurationProperties
{
    private String title;
    private String description;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String version;
}
