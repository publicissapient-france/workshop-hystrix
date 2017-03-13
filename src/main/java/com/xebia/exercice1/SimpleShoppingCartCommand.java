package com.xebia.exercice1;

import java.math.BigDecimal;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xebia.ShoppingCart;

public class SimpleShoppingCartCommand extends HystrixCommand<ShoppingCart> {

    public SimpleShoppingCartCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("CartCommand"));
    }

    @Override
    protected ShoppingCart run() {
        return new ShoppingCart(1, "User", new BigDecimal(100), "description panier");
    }
}
