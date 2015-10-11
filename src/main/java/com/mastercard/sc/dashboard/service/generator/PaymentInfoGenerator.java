package com.mastercard.sc.dashboard.service.generator;

import com.mastercard.sc.dashboard.domain.PaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentInfoGenerator extends AbstractGenerator<PaymentInfo> {

    @Autowired
    private UserGenerator userGenerator;

    @Autowired
    private ItemGenerator itemGenerator;

    private int[] quantities = {1, 1, 2, 1, 1, 3, 1, 1};

    @Override
    public PaymentInfo generate() {
        return new PaymentInfo(
                userGenerator.generate(),
                itemGenerator.generate(),
                quantities[randomInt(quantities.length)]
        );
    }
}
