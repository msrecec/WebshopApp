package com.example.webshop.repository.hnbAPI;

import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Optional;

@Repository
public class HnbRepositoryImpl implements HnbRepository {

    private static final String RESOURCE_URL = "https://api.hnb.hr/tecajn/v1";

    @Override
    public Optional<Hnb> findByCurrency(Currency currency) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(RESOURCE_URL+"?valuta="+currency.getCurrency(), String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Hnb[] hnb = objectMapper.readValue(response.getBody(), Hnb[].class);
            return Optional.ofNullable(hnb[0]);
        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
