package com.xebia;

import java.math.BigDecimal;

public class ShoppingCart {

    private final Integer id;
    private final String user;
    private final BigDecimal amount;
    private final String description;

    public ShoppingCart(Integer id, String user, BigDecimal amount, String description) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
