package com.prokopchuk.noteapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@EnableJpaAuditing
public class ComponentsConfig {

    @Value("${file-service.url}")
    private String fileServiceUrl;

    @Bean
    public RestTemplate fileServiceRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(fileServiceUrl));

        return restTemplate;
    }

}
