package com.xebia.exercice2;

import org.junit.Test;
import com.xebia.ShoppingCart;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleShoppingCartFailureCommandTest {

    @Test
    public void should_test_synchronous_call() {
        ShoppingCart shoppingCart = new SimpleShoppingCartFailureCommand().execute();

        assertThat(shoppingCart.getDescritpion()).isEqualTo("description failure");
        assertThat(shoppingCart.getUser()).isEqualTo("Anonymous user");
    }

}