package com.wooz86.api;

import com.wooz86.framework.application.Command;

import java.util.UUID;

public class ChangeInventoryItemTitleCommand implements Command {
    private final UUID aggregateId;
    private final String title;

    public ChangeInventoryItemTitleCommand(UUID aggregateId, String title) {
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
