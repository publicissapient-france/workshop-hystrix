package com.xebia.exercice4;

import java.math.BigDecimal;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.xebia.ShoppingCart;

public class SimpleShoppingCartCommandException extends HystrixCommand<ShoppingCart> {

    private Integer userId;

    public SimpleShoppingCartCommandException(Integer userId) {
        super(HystrixCommandGroupKey.Factory.asKey("CartCommandException"));
        this.userId = userId;
    }

    @Override
    protected ShoppingCart run() throws Exception {
        if(userId <= 0) {
            throw new HystrixBadRequestException("User ID chas to be superior to 0 ");
        }

        return new ShoppingCart(userId, "User", new BigDecimal(100), "description panier");
    }
}
