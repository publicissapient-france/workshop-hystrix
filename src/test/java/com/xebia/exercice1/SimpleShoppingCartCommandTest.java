package com.xebia.exercice1;

import java.util.concurrent.Future;

import org.junit.Test;

import com.xebia.ShoppingCart;

import rx.Observable;

import static org.assertj.core.api.Assertions.*;

public class SimpleShoppingCartCommandTest {

    @Test
    public void should_test_synchronous_call() {
        ShoppingCart shoppingCart = new SimpleShoppingCartCommand().execute();

        assertThat(shoppingCart.getDescription()).isEqualTo("description panier");
    }

    @Test
    public void should_test_asynchronous_call() throws Exception {
        Future<ShoppingCart> shoppingCart = new SimpleShoppingCartCommand().queue();

        assertThat(shoppingCart.get().getDescription()).isEqualTo("description panier");
    }

    @Test
    public void should_test_observable_call() throws Exception {
        Observable<ShoppingCart> obsShoppingCart = new SimpleShoppingCartCommand().observe();

        ShoppingCart shoppingCart = obsShoppingCart.toBlocking().single();
        assertThat(shoppingCart.getUser()).isEqualTo("User");

        obsShoppingCart.subscribe(shopCart -> assertThat(shopCart.getDescription()).isEqualTo("description panier"), Throwable::printStackTrace);
    }

}
