package com.mastercard.sc.dashboard.domain;

public class CreditCard {

    private String number;

    private int cvc;

    private int expMonth;

    private int expYear;

    public CreditCard(String number, int cvc, int expMonth, int expYear) {
        this.number = number;
        this.cvc = cvc;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "number='" + number + '\'' +
                ", cvc=" + cvc +
                ", expMonth=" + expMonth +
                ", expYear=" + expYear +
                '}';
    }
}
