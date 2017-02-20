package com.xebia.exercice3;

import org.junit.Test;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xebia.ShoppingCart;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleShoppingCartCacheCommandTest {

    @Test
    public void should_test_without_cache_call() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        SimpleShoppingCartCacheCommand cartCacheCommand = new SimpleShoppingCartCacheCommand(12);
        ShoppingCart cartResult = cartCacheCommand.execute();

        assertThat(cartResult.getUser()).isEqualTo("User");

        assertThat(cartCacheCommand.isResponseFromCache()).isFalse();

        context.shutdown();
    }

    @Test
    public void should_test_cache_call() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        SimpleShoppingCartCacheCommand cartCacheCommand_1 = new SimpleShoppingCartCacheCommand(12);

        SimpleShoppingCartCacheCommand cartCacheCommand_2 = new SimpleShoppingCartCacheCommand(12);

        ShoppingCart cartResult_1 = cartCacheCommand_1.execute();
        ShoppingCart cartResult_2 = cartCacheCommand_2.execute();

        assertThat(cartResult_1.getUser()).isEqualTo("User");
        assertThat(cartResult_2.getUser()).isEqualTo("User");

        assertThat(cartCacheCommand_1.isResponseFromCache()).isFalse();
        assertThat(cartCacheCommand_2.isResponseFromCache()).isTrue();

        context.shutdown();
    }

}