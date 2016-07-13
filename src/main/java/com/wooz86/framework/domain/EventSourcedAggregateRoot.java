package com.wooz86.framework.domain;

import java.util.List;
import java.util.UUID;

public interface EventSourcedAggregateRoot {
    UUID getId();
    long getVersion();
    List<Event> getUncommittedEvents();
    void apply(Event event);
    void loadFromHistory(Iterable<Event> events);
}
