package com.example.webshop.repository.hnbAPI;

import com.example.webshop.model.hnb.Currency;
import com.example.webshop.model.hnb.Hnb;

import java.util.Optional;

public interface HnbRepository {
    Optional<Hnb> findByCurrency(Currency currency, String resource);
}
