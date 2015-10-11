package com.mastercard.sc.dashboard.domain.client;

import java.util.HashMap;

public class TotalCounts extends HashMap<String, Double> {

    private final String TOTAL_REVENUE = "totalRevenue";

    private final String TOTAL_REFUND = "totalRefund";

    private final String TOTAL_PURCHASED_ITEMS = "totalPurchasedItems";

    private final String TOTAL_REFUNDED_ITEMS = "totalRefundedItems";

    public TotalCounts() {
        this.put(TOTAL_REVENUE, 0D);
        this.put(TOTAL_REFUND, 0D);
        this.put(TOTAL_PURCHASED_ITEMS, 0D);
        this.put(TOTAL_REFUNDED_ITEMS, 0D);
    }

    public void updateTotalForPayment(int quantity, double itemPrice) {
        this.put(TOTAL_REVENUE, this.get(TOTAL_REVENUE) + ((itemPrice * quantity) / 100));
        this.put(TOTAL_PURCHASED_ITEMS, this.get(TOTAL_PURCHASED_ITEMS) + quantity);
    }

    public void updateTotalForRefund(int quantity, double itemPrice) {
        this.put(TOTAL_REFUND, this.get(TOTAL_REFUND) + ((itemPrice * quantity) / 100));
        this.put(TOTAL_REFUNDED_ITEMS, this.get(TOTAL_REFUNDED_ITEMS) + quantity);
        this.put(TOTAL_REVENUE, this.get(TOTAL_REVENUE) - ((itemPrice * quantity) / 100));
        this.put(TOTAL_PURCHASED_ITEMS, this.get(TOTAL_PURCHASED_ITEMS) - quantity);
    }
}
