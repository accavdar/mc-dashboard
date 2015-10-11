package com.mastercard.sc.dashboard.domain;

public class User {

    private Client client;

    private String location;

    private Currency currency;

    private CreditCard creditCard;

    public User(Client client, String location, Currency currency, CreditCard creditCard) {
        this.client = client;
        this.location = location;
        this.currency = currency;
        this.creditCard = creditCard;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "User{" +
                "client=" + client +
                ", location='" + location + '\'' +
                ", currency=" + currency +
                ", creditCard=" + creditCard +
                '}';
    }
}
