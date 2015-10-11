package com.mastercard.sc.dashboard.service.generator;

import java.util.Random;

public abstract class AbstractGenerator<T> implements Generator<T> {

    protected Random random = new Random();

    protected int randomInt(int range) {
        return random.nextInt(range);
    }
}
