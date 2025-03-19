package com.prokopchuk.reactivenoteapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableR2dbcAuditing
public class ComponentsConfig {

    @Value("${file-service.url}")
    private String fileServiceUrl;

    @Bean
    public WebClient fileServiceWebClient() {
        return WebClient.builder()
            .baseUrl(fileServiceUrl)
            .build();
    }

}
