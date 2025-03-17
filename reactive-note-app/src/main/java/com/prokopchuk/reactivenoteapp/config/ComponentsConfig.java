package com.prokopchuk.reactivenoteapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing
public class ComponentsConfig {

    @Value("${file-service.url}")
    private String fileServiceUrl;

}
