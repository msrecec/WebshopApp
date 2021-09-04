package com.example.webshop.repository.hnbAPI;

import com.example.webshop.config.ApplicationProperties;
import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Optional;

@Component
public class HnbRepositoryImpl implements HnbRepository {

    private final ObjectMapper objectMapper;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public HnbRepositoryImpl(ObjectMapper objectMapper, ApplicationProperties applicationProperties) {
        this.objectMapper = objectMapper;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public Optional<Hnb> findByCurrency(Currency currency) {
        String resource = applicationProperties.getHnbUrl();
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
