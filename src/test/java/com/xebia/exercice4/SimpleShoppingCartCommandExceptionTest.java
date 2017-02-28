package com.xebia.exercice4;

import org.junit.Test;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.xebia.ShoppingCart;

public class SimpleShoppingCartCommandExceptionTest {

    @Test(expected = HystrixBadRequestException.class)
    public void should_test_negative_user_id_call() {
        new SimpleShoppingCartCommandException(-1).execute();
    }
}