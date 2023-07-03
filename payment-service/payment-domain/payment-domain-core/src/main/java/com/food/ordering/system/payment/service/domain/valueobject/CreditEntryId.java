package com.food.ordering.system.payment.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseID;

import java.util.UUID;

public class CreditEntryId extends BaseID<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
