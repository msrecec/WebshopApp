package com.example.webshop.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("app.hnb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationProperties {

    private String hnbUrl = "https://api.hnb.hr/tecajn/v1?valuta=";

}
