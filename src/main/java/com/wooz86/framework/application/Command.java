package com.wooz86.framework.application;

import com.wooz86.framework.domain.Message;

import java.util.UUID;

public interface Command extends Message {
    UUID getAggregateId();
}
