package com.xebia.exercice5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.netflix.hystrix.HystrixCommand;
import com.xebia.ShoppingCart;

import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

public class SimpleShoppingCartBatchCommand extends HystrixCommand<List<ShoppingCart>> {

    private List<Integer> ids;

    protected SimpleShoppingCartBatchCommand(List<Integer> ids) {
        super(Setter.withGroupKey(asKey("Shopping Cart batch command")));
        this.ids = ids;
    }

    @Override
    protected List<ShoppingCart> run() throws Exception {

        List<ShoppingCart> carts = new ArrayList<>();

        for (Integer id:ids) {
            carts.add(new ShoppingCart(id, "User", new BigDecimal(100), "description panier"));
        }

        return carts;
    }
}
