package com.mastercard.sc.dashboard.domain;

public class RefundInfo {

    private String paymentId;

    private String amount;

    private String reason;

    public RefundInfo(String paymentId, String amount, String reason) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.reason = reason;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }
}
