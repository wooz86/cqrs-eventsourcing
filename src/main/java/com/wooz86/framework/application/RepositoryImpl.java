package com.wooz86.framework.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wooz86.framework.domain.AbstractEventSourcedAggregateRoot;
import com.wooz86.framework.domain.Event;
import com.wooz86.framework.domain.Repository;
import com.wooz86.framework.eventstore.AggregateNotFoundException;
import com.wooz86.framework.eventstore.ConcurrencyException;
import com.wooz86.framework.eventstore.EventStore;
import com.wooz86.framework.eventstore.inmemory.InMemoryEventStore;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class RepositoryImpl<T extends AbstractEventSourcedAggregateRoot> implements Repository {

    final Class<T> clazz;
    private final EventStore eventStore;

    public RepositoryImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.eventStore = new InMemoryEventStore();
    }

    //    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractEventSourcedAggregateRoot> T getById(UUID id) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, AggregateNotFoundException {
        T obj = (T) clazz.newInstance();
        List<Event> events = eventStore.getEventsForAggregate(id);
        obj.loadFromHistory(events);

        return obj;
    }

    @Override
    public <T extends AbstractEventSourcedAggregateRoot> void save(T aggregateRoot, long originatingVersion) throws JsonProcessingException, ConcurrencyException {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedEvents(), originatingVersion);
    }
}
