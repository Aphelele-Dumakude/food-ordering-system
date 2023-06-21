package com.food.ordering.system.domain.valueobject;

import java.util.UUID;

public class RestaurantID extends BaseID<UUID>{
    protected RestaurantID(UUID value) {
        super(value);
    }
}
