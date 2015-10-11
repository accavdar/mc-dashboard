package com.mastercard.sc.dashboard.service.generator;

import com.mastercard.sc.dashboard.domain.CreditCard;
import org.springframework.stereotype.Component;

@Component
public class CreditCardGenerator extends AbstractGenerator<CreditCard> {

    private String[] creditCardNumbers = {
            "5222222222222200", // LOST
            "5343434343434343", // STOLEN
            "5105105105105100", // FRAUD
            "5305305305305300", // CAPTURE CARD
            "6011111111111117", // UNAUTHORIZED USE
            "4444333322221111", // COUNTERFEIT
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444",
            "5555555555554444"
    };

    private int[] cvcs = {123, 456, 135, 789, 873, 729, 031, 946};

    private int[] expMonths = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    private int[] expYears = {16, 17, 18, 19, 20};

    @Override
    public CreditCard generate() {
        return new CreditCard(
                creditCardNumbers[randomInt(creditCardNumbers.length)],
                cvcs[randomInt(cvcs.length)],
                expMonths[randomInt(expMonths.length)],
                expYears[randomInt(expYears.length)]
        );
    }
}
