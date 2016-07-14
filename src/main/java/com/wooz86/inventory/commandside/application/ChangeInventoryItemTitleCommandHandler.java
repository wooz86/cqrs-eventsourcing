package com.wooz86.inventory.commandside.application;

import com.wooz86.api.ChangeInventoryItemTitleCommand;
import com.wooz86.framework.application.CommandHandler;
import com.wooz86.framework.domain.Repository;
import com.wooz86.framework.eventstore.AggregateNotFoundException;
import com.wooz86.framework.eventstore.ConcurrencyException;
import com.wooz86.inventory.commandside.domain.model.InventoryItem;

import java.io.IOException;

public class ChangeInventoryItemTitleCommandHandler implements CommandHandler<ChangeInventoryItemTitleCommand> {

    private final Repository repository;

    public ChangeInventoryItemTitleCommandHandler(Repository repository) {
        this.repository = repository;
    }

    public void handle(ChangeInventoryItemTitleCommand command) throws IllegalAccessException, IOException, ClassNotFoundException, ConcurrencyException, AggregateNotFoundException, InstantiationException {
        // Get from repository
        InventoryItem inventoryItem = repository.getById(command.getAggregateId());
        long originatingVersion = inventoryItem.getVersion();

        // Apply command
        inventoryItem.changeTitle(command.getTitle());

        // Persist generated events
        repository.save(inventoryItem, originatingVersion);

        // @todo publish events to event bus, this should be refactored later to avoid 2PC

        inventoryItem.markEventsAsCommitted();
    }
}
