package com.example.webshop.config.hnb;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(HnbApplicationProperties.class)
public class HnbRepositoryConfig {

    @Bean
    @Primary
    HnbApplicationProperties getHnbUrl() {
        return new HnbApplicationProperties();
    }

}
