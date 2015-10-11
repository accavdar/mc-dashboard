package com.mastercard.sc.dashboard.domain.client;

import java.util.HashMap;

public class Transaction extends HashMap<String, String> {
    public Transaction(String paymentId, String date, String itemName, String amount,
                       String quantity, String currency, String client, String paymentStatus) {
        this.put("paymentId", paymentId);
        this.put("paymentDate", date);
        this.put("itemName", itemName);
        this.put("amount", amount);
        this.put("quantity", quantity);
        this.put("currency", currency);
        this.put("client", client);
        this.put("paymentStatus", paymentStatus);
    }

    public String getPaymentId() {
        return this.get("paymentId");
    }

    public String getPaymentDate() {
        return this.get("paymentDate");
    }

    public String getItemName() {
        return this.get("itemName");
    }

    public String getAmount() {
        return this.get("amount");
    }

    public String getQuantity() {
        return this.get("quantity");
    }

    public String getCurrency() {
        return this.get("currency");
    }

    public String getClient() {
        return this.get("client");
    }

    public void setPaymentStatus(String status) {
        this.put("paymentStatus", status);
    }
}
