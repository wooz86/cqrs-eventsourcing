package com.wooz86.framework.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractEventSourcedAggregateRoot implements EventSourcedAggregateRoot {
    protected final List<Event> uncommittedEvents = new ArrayList<>();
    protected UUID id;
    private long version = -1;

    public UUID getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public List<Event> getUncommittedEvents() {
        return uncommittedEvents;
    }

    public void markEventsAsCommitted() {
        uncommittedEvents.clear();
    }

    protected void incrementVersion() {
        version++;
    }
}
