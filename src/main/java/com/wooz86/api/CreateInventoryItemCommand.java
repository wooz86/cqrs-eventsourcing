package com.wooz86.api;

import com.wooz86.framework.application.Command;

import java.util.UUID;

public class CreateInventoryItemCommand implements Command {
    private final UUID aggregateId;
    private final String title;
    private final double price;

    public CreateInventoryItemCommand(UUID aggregateId, String title, double price) {
        this.aggregateId = aggregateId;
        this.title = title;
        this.price = price;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }
}
