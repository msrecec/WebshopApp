package com.example.webshop.model.hnb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Hnb {
    @JsonProperty("Broj tečajnice")
    private String brojTecajnice;
    @JsonProperty("Datum primjene")
    private String datumPrimjene;
    @JsonProperty("Država")
    private String drzava;
    @JsonProperty("Šifra valute")
    private String sifraValute;
    @JsonProperty("Valuta")
    private String valuta;
    @JsonProperty("Jedinica")
    private Integer jedinica;
    @JsonProperty("Kupovni za devize")
    private String kupovniZaDevize;
    @JsonProperty("Srednji za devize")
    private String srednjiZaDevize;
    @JsonProperty("Prodajni za devize")
    private String prodajniZaDevize;
}
