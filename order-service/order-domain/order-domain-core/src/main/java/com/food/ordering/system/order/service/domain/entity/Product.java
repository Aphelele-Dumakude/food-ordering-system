package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productID, String name, Money price) {
        super.setId(productID);
        this.name = name;
        this.price = price;
    }
    public Product(ProductId productID) {
        super.setId(productID);
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void updateWithConfirmedNaemAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
