package com.wooz86.inventory.commandside.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wooz86.api.CreateInventoryItemCommand;
import com.wooz86.framework.application.CommandHandler;
import com.wooz86.framework.domain.Repository;
import com.wooz86.framework.eventstore.ConcurrencyException;
import com.wooz86.inventory.commandside.domain.model.InventoryItem;

public class CreateInventoryItemCommandHandler implements CommandHandler<CreateInventoryItemCommand> {

    private final Repository repository;

    public CreateInventoryItemCommandHandler(Repository repository) {
        this.repository = repository;
    }

    public void handle(CreateInventoryItemCommand command) throws JsonProcessingException, ConcurrencyException {

        // Apply command by instantiating a new inventory item
        InventoryItem inventoryItem = new InventoryItem(command.getAggregateId(), command.getTitle(), command.getPrice());

        // Persist generated events
        repository.save(inventoryItem, -1);

        // @todo publish events to event bus, this should be refactored later to avoid 2PC

        inventoryItem.markEventsAsCommitted();
    }
}
