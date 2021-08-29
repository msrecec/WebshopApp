package com.example.webshop.model.hnb;

public enum Currency {
    AUD("AUD"), CAD("CAD"), CZK("CZK"), DKK("DKK"),
    HUF("HUF"), JPY("JPY"), NOK("NOK"), SEK("SEK"),
    CHF("CHF"),GPB("GPB"), USD("USD"), BAM("BAM"),
    EUR("EUR"), PLN("PLN");

    public final String currency;

    private Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currency='" + currency + '\'' +
                '}';
    }
}
