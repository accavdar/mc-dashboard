package com.mastercard.sc.dashboard.domain;

public class PaymentInfo {

    private User user;

    private Item item;

    private int quantity;

    public PaymentInfo(User user, Item item, int quantity) {
        this.user = user;
        this.item = item;
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "user=" + user +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
