package com.xebia;

import java.math.BigDecimal;

public class ShoppingCart {

    private Integer id;
    private String user;
    private BigDecimal amount;
    private String descritpion;

    public ShoppingCart(final Integer id, final String user, final BigDecimal amount, final String description) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.descritpion = description;
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

    public String getDescritpion() {
        return descritpion;
    }
}
