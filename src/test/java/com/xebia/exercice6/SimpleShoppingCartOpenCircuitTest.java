package com.xebia.exercice6;

import org.junit.Test;
import com.xebia.ShoppingCart;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleShoppingCartOpenCircuitTest {

    @Test
    public void should_test_open_circuit() throws Exception {
        ShoppingCart shoppingCart1 = new SimpleShoppingCartOpenCircuit(1).execute();

        assertThat(shoppingCart1.getId()).isEqualTo(0);

        SimpleShoppingCartOpenCircuit simpleShoppingCartOpenCircuit = new SimpleShoppingCartOpenCircuit(2);
        ShoppingCart shoppingCart2 = simpleShoppingCartOpenCircuit.execute();
        
        assertThat(shoppingCart2.getId()).isEqualTo(0);

        new SimpleShoppingCartOpenCircuit(3).execute();
        new SimpleShoppingCartOpenCircuit(4).execute();

        SimpleShoppingCartOpenCircuit simpleShoppingCartOpenCircuit5 = new SimpleShoppingCartOpenCircuit(5);
        ShoppingCart shoppingCart5 = simpleShoppingCartOpenCircuit5.execute();

        assertThat(shoppingCart5.getId()).isEqualTo(0);
        assertThat(simpleShoppingCartOpenCircuit5.isCircuitBreakerOpen()).isFalse();

        ShoppingCart shoppingCart6 = new SimpleShoppingCartOpenCircuit(6).execute();

        assertThat(shoppingCart6.getId()).isEqualTo(0);
        assertThat(simpleShoppingCartOpenCircuit5.isCircuitBreakerOpen()).isTrue();

    }
}