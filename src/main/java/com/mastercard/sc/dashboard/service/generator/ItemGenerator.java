package com.mastercard.sc.dashboard.service.generator;

import com.mastercard.sc.dashboard.domain.Currency;
import com.mastercard.sc.dashboard.domain.Item;
import com.mastercard.sc.dashboard.domain.Price;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemGenerator extends AbstractGenerator<Item> {

    private final int ITEM_COUNT = 5;

    private List<Item> items = new ArrayList<>();

    private Double[] priceAmounts = {2000D, 4000D, 1000D, 5000D, 6000D};

    public ItemGenerator() {
        initializeItems();
    }

    @Override
    public Item generate() {
        return items.get(randomInt(items.size()));
    }

    private void initializeItems() {
        for (int i = 0; i < ITEM_COUNT; i++) {
            Item item = new Item("Item " + (i + 1), new Price(priceAmounts[i], Currency.USD));
            items.add(item);
        }
    }
}
