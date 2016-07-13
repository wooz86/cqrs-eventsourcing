package com.wooz86.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wooz86.framework.application.Command;

import java.util.UUID;

public class ReduceInventoryItemPriceCommand implements Command {

    private final UUID aggregateId;
    private final double priceReduction;

    @JsonCreator
    public ReduceInventoryItemPriceCommand(@JsonProperty("aggregateId") UUID aggregateId,
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
