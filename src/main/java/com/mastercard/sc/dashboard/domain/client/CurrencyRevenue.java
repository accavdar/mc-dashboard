package com.mastercard.sc.dashboard.domain.client;

import com.mastercard.sc.dashboard.domain.Currency;

import java.util.HashMap;

public class CurrencyRevenue extends HashMap<String, Double> {

    public CurrencyRevenue() {
        for (Currency c : Currency.values()) {
            this.put(c.toString(), 0D);
        }
    }

    public void updateRevenueForPayment(String currency, Double amount) {
        this.put(currency, this.get(currency) + (amount / 100));
    }

    public void updateRevenueForRefund(String currency, Double amount) {
        this.put(currency, this.get(currency) - (amount / 100));
    }
}
