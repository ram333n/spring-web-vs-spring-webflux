package com.prokopchuk.noteapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentsConfig {

    @Value("${file-service.url}")
    private String fileServiceUrl;

}
