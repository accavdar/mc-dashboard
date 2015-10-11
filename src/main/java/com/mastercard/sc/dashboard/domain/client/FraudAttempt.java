package com.mastercard.sc.dashboard.domain.client;

import java.util.HashMap;

public class FraudAttempt extends HashMap<String, String> {

    public FraudAttempt(String title, String accountNumber, String reason,
                        String date, String itemName, String amount, String quantity) {
        this.put("title", title);
        this.put("accountNumber", accountNumber);
        this.put("fraudReason", reason);
        this.put("fraudDate", date);
        this.put("itemName", itemName);
        this.put("amount", amount);
        this.put("quantity", quantity);
    }
}
