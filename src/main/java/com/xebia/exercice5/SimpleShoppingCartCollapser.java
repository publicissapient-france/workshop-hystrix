package com.xebia.exercice5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.xebia.ShoppingCart;

public class SimpleShoppingCartCollapser extends HystrixCollapser<List<ShoppingCart>, ShoppingCart, Integer> {

    private Integer id;

    public SimpleShoppingCartCollapser(final Integer id) {
        this.id = id;
    }

    @Override
    public Integer getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<List<ShoppingCart>> createCommand(Collection<CollapsedRequest<ShoppingCart, Integer>> collapsedRequests) {
        final List<Integer> ids = new ArrayList<>(collapsedRequests.size());

        ids.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument)
            .collect(Collectors.toList()));

        return new SimpleShoppingCartBatchCommand(ids);
    }

    @Override
    protected void mapResponseToRequests(List<ShoppingCart> shoppingCarts, Collection<CollapsedRequest<ShoppingCart, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<ShoppingCart, Integer> collapsedRequest : collapsedRequests) {
            collapsedRequest.setResponse(shoppingCarts.get(count++));
        }
    }
}
