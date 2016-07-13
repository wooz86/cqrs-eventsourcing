package com.wooz86.framework.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wooz86.framework.eventstore.AggregateNotFoundException;
import com.wooz86.framework.eventstore.ConcurrencyException;

import java.io.IOException;
import java.util.UUID;

public interface Repository {
    <T extends AbstractEventSourcedAggregateRoot> T getById(UUID id) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, AggregateNotFoundException;

    <T extends AbstractEventSourcedAggregateRoot> void save(T aggregateRoot, long originatingVersion) throws JsonProcessingException, ConcurrencyException;
}
