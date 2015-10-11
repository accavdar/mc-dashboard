package com.mastercard.sc.dashboard.service.payment;

import com.mastercard.sc.dashboard.domain.PaymentInfo;
import com.mastercard.sc.dashboard.domain.PaymentResult;
import com.mastercard.sc.dashboard.domain.RefundInfo;

public interface PaymentService extends LostStolenService {

    PaymentResult payment(PaymentInfo paymentInfo);

    boolean refund(RefundInfo refundInfo);
}
