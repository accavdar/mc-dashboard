package com.mastercard.sc.dashboard.domain.client;

import java.util.HashMap;

public class ItemPaymentRefund extends HashMap<String, String> {

    public ItemPaymentRefund(String itemName) {
        this.put("item", itemName);
        this.put("PAYMENT", String.valueOf(0));
        this.put("REFUND", String.valueOf(0));
    }

    public void updateRevenueForPayment(int quantity) {
        this.put("PAYMENT", String.valueOf(Integer.valueOf(this.get("PAYMENT")) + quantity));
    }

    public void updateRevenueForRefund(int quantity) {
        this.put("REFUND", String.valueOf(Integer.valueOf(this.get("REFUND")) + quantity));
    }
}
