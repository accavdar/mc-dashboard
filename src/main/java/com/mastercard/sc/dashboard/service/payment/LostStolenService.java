package com.mastercard.sc.dashboard.service.payment;

import com.mastercard.sc.dashboard.domain.Account;

public interface LostStolenService {

    Account getLostStolen(String accountNumber);
}
