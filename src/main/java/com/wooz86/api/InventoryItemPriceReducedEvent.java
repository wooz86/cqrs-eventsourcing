package com.wooz86.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wooz86.framework.domain.Event;

import java.util.UUID;

public class InventoryItemPriceReducedEvent implements Event {

    private final UUID aggregateId;
    private final double priceReduction;

    @JsonCreator
    public InventoryItemPriceReducedEvent(@JsonProperty("aggregateId") UUID aggregateId,
                                          @JsonProperty("priceReduction") double priceReduction) {
        this.aggregateId = aggregateId;
        this.priceReduction = priceReduction;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }

    public double getPriceReduction() {
        return priceReduction;
    }
}
