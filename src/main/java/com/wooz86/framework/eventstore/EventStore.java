package com.wooz86.framework.eventstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wooz86.framework.domain.Event;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EventStore {
    void saveEvents(UUID aggregateRootId, List<Event> events, long expectedVersion) throws JsonProcessingException, ConcurrencyException;
    List<Event> getEventsForAggregate(UUID aggregateId) throws IOException, ClassNotFoundException, AggregateNotFoundException;
}
