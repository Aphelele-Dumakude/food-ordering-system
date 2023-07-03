package com.food.ordering.system.payment.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseID;

import java.util.UUID;

public class CreditHistoyId extends BaseID<UUID> {
    protected CreditHistoyId(UUID value) {
        super(value);
    }
}
