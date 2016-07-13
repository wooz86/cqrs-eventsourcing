package com.wooz86.framework.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wooz86.framework.eventstore.AggregateNotFoundException;
import com.wooz86.framework.eventstore.ConcurrencyException;

import java.io.IOException;

public interface CommandHandler<T> {
    void handle(T command) throws InstantiationException, JsonProcessingException, ConcurrencyException, IllegalAccessException, IOException, ClassNotFoundException, AggregateNotFoundException;
}
