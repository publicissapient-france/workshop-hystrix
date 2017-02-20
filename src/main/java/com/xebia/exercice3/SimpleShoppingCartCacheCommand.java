package com.xebia.exercice3;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xebia.ShoppingCart;

public class SimpleShoppingCartCacheCommand extends HystrixCommand<ShoppingCart> {

    private Integer id;

    private Map<Integer, ShoppingCart> mapShoppingCart = new HashMap<>();

    public SimpleShoppingCartCacheCommand(Integer id) {
        super(HystrixCommandGroupKey.Factory.asKey("CartCacheCommand"));

        this.id = id;
    }

    @Override
    protected ShoppingCart run() throws Exception {
        ShoppingCart cart = new ShoppingCart(id, "User", BigDecimal.TEN, "description panier");
        mapShoppingCart.putIfAbsent(id, cart);

        return cart;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }
}