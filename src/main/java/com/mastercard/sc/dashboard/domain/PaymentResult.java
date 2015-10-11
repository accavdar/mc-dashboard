package com.mastercard.sc.dashboard.domain;

public class PaymentResult {

    private String paymentId;

    private Long paymentDate;

    private String paymentStatus;

    private PaymentInfo paymentInfo;

    public PaymentResult(String paymentId, Long paymentDate, String paymentStatus, PaymentInfo paymentInfo) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.paymentInfo = paymentInfo;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Long paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
