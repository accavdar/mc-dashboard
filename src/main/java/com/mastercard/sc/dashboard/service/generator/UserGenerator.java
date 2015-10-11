package com.mastercard.sc.dashboard.service.generator;

import com.mastercard.sc.dashboard.domain.Client;
import com.mastercard.sc.dashboard.domain.Currency;
import com.mastercard.sc.dashboard.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserGenerator extends AbstractGenerator<User> {

    @Autowired
    private CreditCardGenerator creditCardGenerator;

    private Client[] clients = Client.values();

    private String[] locations = {
            "United States",
            "France",
            "Germany",
            "Spain",
            "Britain"
    };

    private Currency[] currencies = {Currency.USD, Currency.EUR, Currency.USD, Currency.EUR,
            Currency.GBP, Currency.USD, Currency.USD, Currency.USD};

    @Override
    public User generate() {
        return new User(
                clients[randomInt(clients.length)],
                locations[randomInt(locations.length)],
                currencies[randomInt(currencies.length)],
                creditCardGenerator.generate()
        );
    }
}
