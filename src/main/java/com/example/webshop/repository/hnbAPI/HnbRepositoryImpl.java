package com.example.webshop.repository.hnbAPI;

import com.example.webshop.config.HnbApplicationProperties;
import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class HnbRepositoryImpl implements HnbRepository {

    private final ObjectMapper objectMapper;
    private final HnbApplicationProperties hnbApplicationProperties;

    @Autowired
    public HnbRepositoryImpl(ObjectMapper objectMapper, HnbApplicationProperties hnbApplicationProperties) {
        this.objectMapper = objectMapper;
        this.hnbApplicationProperties = hnbApplicationProperties;
    }

    @Override
    public Optional<Hnb> findByCurrency(Currency currency) {
        String resource = hnbApplicationProperties.getUrl();
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(resource+currency.getCurrency(), String.class);
            Hnb[] hnb = objectMapper.readValue(response.getBody(), Hnb[].class);
            return Optional.ofNullable(hnb[0]);
        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
