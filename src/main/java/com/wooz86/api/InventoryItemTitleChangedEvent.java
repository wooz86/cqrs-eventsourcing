package com.wooz86.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wooz86.framework.domain.Event;

import java.util.UUID;

public class InventoryItemTitleChangedEvent implements Event {

    private final UUID aggregateId;
    private final String title;

    @JsonCreator
    public InventoryItemTitleChangedEvent(@JsonProperty("aggregateId") UUID aggregateId,
                                          @JsonProperty("title") String title) {

        this.aggregateId = aggregateId;
        this.title = title;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getTitle() {
        return title;
    }
}
