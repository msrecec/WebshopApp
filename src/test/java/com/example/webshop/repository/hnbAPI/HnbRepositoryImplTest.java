package com.example.webshop.repository.hnbAPI;

import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;
import static org.mockito.BDDMockito.given;


import java.util.Optional;

@SpringBootTest
class HnbRepositoryImplTest {

    @Autowired
    HnbRepository underTest;

    private static final String RESOURCE_URL = "http://localhost:8081/tecajn/v1?valuta=";
    private static final Currency CURRENCY = Currency.EUR;

    @Test
    void findByCurrencyExistsTest() {

        // given

        // when

        Optional<Hnb> hnb = underTest.findByCurrency(CURRENCY, RESOURCE_URL);

        // then

        assertThat(hnb.isPresent()).isTrue();
        assertThat(hnb.get().getValuta()).isEqualToIgnoringCase("EUR");
        assertThat(hnb.get().getDrzava()).isEqualToIgnoringCase("EMU");

    }
}