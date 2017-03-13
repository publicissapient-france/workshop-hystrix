package com.xebia.exercice6;

import java.math.BigDecimal;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.xebia.ShoppingCart;

public class SimpleShoppingCartOpenCircuit extends HystrixCommand<ShoppingCart> {

    private Integer id;

    protected SimpleShoppingCartOpenCircuit(Integer id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(SimpleShoppingCartOpenCircuit.class.getName())).andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter()
                .withCircuitBreakerRequestVolumeThreshold(5)));

        this.id = id;
    }

    @Override
    protected ShoppingCart run() throws InterruptedException {
        Thread.sleep(500);

        throw new RuntimeException("Failed!");
    }

    @Override
    protected ShoppingCart getFallback() {
        return new ShoppingCart(0, "Anonymous user", BigDecimal.ZERO, "description failure");
    }
}
