package com.wooz86.framework.eventstore.inmemory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooz86.framework.domain.Event;
import com.wooz86.framework.eventstore.AggregateNotFoundException;
import com.wooz86.framework.eventstore.ConcurrencyException;
import com.wooz86.framework.eventstore.EventStore;

import java.io.IOException;
import java.util.*;

public class InMemoryEventStore implements EventStore {

    private final Map<UUID, List<EventData>> current = new HashMap<>();
    private final ObjectMapper serializer;

    public InMemoryEventStore() {
        this.serializer = new ObjectMapper();
    }

    @Override
    public void saveEvents(UUID aggregateRootId, List<Event> events, long expectedVersion) throws JsonProcessingException, ConcurrencyException {
        List<EventData> eventStream = current.get(aggregateRootId);

        if (eventStream == null) {
            eventStream = new ArrayList<>();
            current.put(aggregateRootId, eventStream);
        } else if (eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion && expectedVersion != -1) {
            throw new ConcurrencyException();
        }

        long i = expectedVersion;

        for (Event e : events) {
            i++;
            String payload = serializer.writeValueAsString(e);
            eventStream.add(new EventData(UUID.randomUUID(), aggregateRootId, i, e.getClass().getName(), payload, new Date()));
        }
    }

    @Override
    public List<Event> getEventsForAggregate(UUID aggregateId) throws IOException, ClassNotFoundException, AggregateNotFoundException {
        List<EventData> eventStream = current.get(aggregateId);

        if (eventStream == null) {
            throw new AggregateNotFoundException();
        }

        List<Event> events = new ArrayList<>();
        for (EventData e : eventStream) {
            Class<?> eventClass = Class.forName(e.getEventType());
            Object event = serializer.readValue(e.getPayload(), eventClass);
            events.add((Event) event);
        }

        return events;
    }
}
