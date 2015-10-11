package com.mastercard.sc.dashboard.util;

import com.mastercard.sc.dashboard.domain.Currency;
import com.mastercard.sc.dashboard.domain.Price;

import java.util.HashMap;
import java.util.Map;

public class PriceConverter {

    private static Map<Currency, Double> coefficient = new HashMap<>();

    static {
        coefficient.put(Currency.USD, 1.0);
        coefficient.put(Currency.EUR, 0.85);
        coefficient.put(Currency.GBP, 0.65);
    }

    // assuming default price is USD
    public static Price getPrice(Price price, Currency targetCurrency) {
        return new Price(price.getAmount() * coefficient.get(targetCurrency), targetCurrency);
    }
}
