package com.wooz86.api;

import com.wooz86.framework.application.Command;

import java.util.UUID;

public class IncreaseInventoryItemPriceCommand implements Command {

    private final UUID aggregateId;
    private final double priceIncrease;

    public IncreaseInventoryItemPriceCommand(UUID aggregateId, double priceIncrease) {
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
