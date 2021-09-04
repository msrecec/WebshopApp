package com.example.webshop.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class HnbRepositoryConfig {

    @Bean
    @Primary
    ApplicationProperties getHnbUrl() {
        return new ApplicationProperties();
    }

}
