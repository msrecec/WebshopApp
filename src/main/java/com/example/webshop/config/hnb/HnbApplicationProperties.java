package com.example.webshop.config.hnb;

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
public class HnbApplicationProperties {

    private String url = "https://api.hnb.hr/tecajn/v1?valuta=";

}
