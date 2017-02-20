package com.xebia.exercice2;

import java.math.BigDecimal;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xebia.ShoppingCart;

public class SimpleShoppingCartFailureCommand extends HystrixCommand<ShoppingCart> {

    public SimpleShoppingCartFailureCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("CartFailureCommand"));
    }

    @Override
    protected ShoppingCart run() throws Exception {
        Thread.sleep(1000);
        return new ShoppingCart(1, "User", BigDecimal.TEN, "description panier");
    }

    @Override
    protected ShoppingCart getFallback() {
        return new ShoppingCart(0, "Anonymous user", BigDecimal.ZERO, "description failure");
    }
}