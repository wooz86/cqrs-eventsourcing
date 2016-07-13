package com.wooz86.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wooz86.framework.domain.Event;

import java.util.UUID;

public class InventoryItemCreatedEvent implements Event {

    private final UUID aggregateId;
    private final String title;
    private final double price;

//    public InventoryItemCreatedEvent(UUID aggregateId, String title, double price) {
//        this.aggregateId = aggregateId;
//        this.title = title;
//        this.price = price;
//    }

    @JsonCreator
    public InventoryItemCreatedEvent(@JsonProperty("aggregateId") UUID aggregateId,
                                     @JsonProperty("title") String title,
                                     @JsonProperty("price") double price) {
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
