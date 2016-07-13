package com.wooz86.framework.domain;

import java.util.UUID;

public interface Event extends Message {
    UUID getAggregateId();
}
