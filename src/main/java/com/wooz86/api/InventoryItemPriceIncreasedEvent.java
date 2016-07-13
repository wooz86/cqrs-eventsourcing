package com.wooz86.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wooz86.framework.domain.Event;

import java.util.UUID;

public class InventoryItemPriceIncreasedEvent implements Event {

    private final UUID aggregateId;
    private final double priceIncrease;

    @JsonCreator
    public InventoryItemPriceIncreasedEvent(@JsonProperty("aggregateId") UUID aggregateId,
                                            @JsonProperty("priceIncrease") double priceIncrease) {
        this.aggregateId = aggregateId;
        this.priceIncrease = priceIncrease;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }

    public double getPriceIncrease() {
        return priceIncrease;
    }
}
