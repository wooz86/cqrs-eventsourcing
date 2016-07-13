package com.wooz86.framework.eventstore.cassandra;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wooz86.framework.domain.Event;
import com.wooz86.framework.eventstore.EventStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.asc;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

public class CassandraEventStore implements EventStore {

    private static final String HOST = "192.168.99.100";
    private static final int PORT = 9042;

    private final Session cassandra;
    private final Mapper<EventData> mapper;
    private final ObjectMapper serializer;

    public CassandraEventStore() {
        Cluster cluster = Cluster.builder()
                .addContactPoint(HOST)
                .withPort(PORT)
                .build();

        this.cassandra = cluster.connect("test");

        MappingManager mappingManager = new MappingManager(cassandra);
        this.mapper = mappingManager.mapper(EventData.class);

        this.serializer = new ObjectMapper();

        setupSchema();
    }

    private void setupSchema() {
        cassandra.execute("CREATE KEYSPACE IF NOT EXISTS test WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':1};");

        cassandra.execute("CREATE TABLE IF NOT EXISTS test.events(" +
                "id uuid, " +
                "aggregateId uuid, " +
                "version bigint, " +
                "eventType varchar, " +
                "payload varchar, " +
                "timestamp timestamp, " +
                "PRIMARY KEY((aggregateId), version))"
        );

        // @todo Create an index for aggregateId
    }

    @Override
    public void saveEvents(UUID aggregateId, List<Event> events, long expectedVersion) throws JsonProcessingException {
        PreparedStatement prepared = cassandra.prepare("INSERT INTO test.events " +
                "(id, aggregateId, version, eventType, payload, timestamp) VALUES (?, ?, ?, ?, ?, ?)"
        );

        long v = expectedVersion;

        // @todo Check version against Cassandra

        for (Event e : events) {
            v++;
            String payload = serializer.writeValueAsString(e);

            BoundStatement bound = prepared.bind(
                    UUID.randomUUID(),
                    aggregateId,
                    v,
                    e.getClass().getName(),
                    payload,
                    new Date()
            );

            cassandra.execute(bound);
        }

    }

    @Override
    public List<Event> getEventsForAggregate(UUID aggregateId) throws IOException, ClassNotFoundException {
        Statement statement = QueryBuilder.select().all()
                .from("test", "events")
                .where(eq("aggregateId", aggregateId))
                .orderBy(asc("version"));

        ResultSet results = cassandra.execute(statement);

        Result<EventData> eventData = mapper.map(results);
        List<Event> events = deserializeResults(eventData);

        return events;
    }

    private List<Event> deserializeResults(Result<EventData> eventData) throws ClassNotFoundException, IOException {
        List<Event> events = new ArrayList<>();
        for (EventData e : eventData) {
            Class<?> eventClass = Class.forName(e.getEventType());
            Object event = serializer.readValue(e.getPayload(), eventClass);
            events.add((Event) event);
        }

        return events;
    }
}
