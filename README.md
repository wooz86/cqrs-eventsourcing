# cqrs-eventsourcing
My first attempt at implementing a naive event sourcing / CQRS solution using a Java application based on Spring Framework (Spring Boot).

There's still a lot of things to be done. This is just my first attempt at implementing this technique.
The project includes a basic framework for cqrs and event sourcing (located in framework/), an API-package containing the
commands and events to be used when interacting with aggregates, either by processing published events or by issuing a command to an aggregate.

#### TODOs and improvements
* Implement tests
* Implement read side for the inventory item aggregate.
* Implement a Command Bus
* Implement an Event Bus
