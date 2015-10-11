package com.mastercard.sc.dashboard.domain.client;

import com.mastercard.sc.dashboard.domain.Client;

import java.util.HashMap;

public class ClientRevenue extends HashMap<String, Double> {

    public ClientRevenue() {
        for (Client c : Client.values()) {
            this.put(c.getName(), 0D);
        }
    }

    public void updateRevenueForPayment(String client, Double amount) {
        this.put(client, this.get(client) + (amount / 100));
    }

    public void updateRevenueForRefund(String client, Double amount) {
        this.put(client, this.get(client) - (amount / 100));
    }
}
