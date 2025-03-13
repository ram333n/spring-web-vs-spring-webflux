package com.prokopchuk.noteapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class ComponentsConfig {

    @Value("${file-service.url}")
    private String fileServiceUrl;

}
