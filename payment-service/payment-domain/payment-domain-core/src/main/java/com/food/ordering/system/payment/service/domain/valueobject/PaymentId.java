package com.food.ordering.system.payment.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseID;

import java.util.UUID;

public class PaymentId extends BaseID<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
