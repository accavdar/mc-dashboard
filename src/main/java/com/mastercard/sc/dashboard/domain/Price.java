package com.mastercard.sc.dashboard.domain;

public class Price {

    private Currency currency;

    private Double amount;

    public Price(Double amount) {
        this(amount, Currency.USD);
    }

    public Price(Double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Price{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
