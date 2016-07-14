package com.wooz86.inventory.commandside.application;

import com.wooz86.api.ReduceInventoryItemPriceCommand;
import com.wooz86.framework.application.CommandHandler;
import com.wooz86.framework.domain.Repository;
import com.wooz86.framework.eventstore.AggregateNotFoundException;
import com.wooz86.framework.eventstore.ConcurrencyException;
import com.wooz86.inventory.commandside.domain.model.InventoryItem;

import java.io.IOException;

public class ReduceInventoryItemPriceCommandHandler implements CommandHandler<ReduceInventoryItemPriceCommand> {

    private final Repository repository;

    public ReduceInventoryItemPriceCommandHandler(Repository repository) {
        this.repository = repository;
    }

    public void handle(ReduceInventoryItemPriceCommand command) throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, ConcurrencyException, AggregateNotFoundException {

        // Get from repository
        InventoryItem inventoryItem = repository.getById(command.getAggregateId());
        long originatingVersion = inventoryItem.getVersion();

        // Apply command
        inventoryItem.reducePrice(command.getPriceReduction());

        // Persist generated events
        repository.save(inventoryItem, originatingVersion);

        // @todo publish events to event bus, this should be refactored later to avoid 2PC

        inventoryItem.markEventsAsCommitted();
    }
}
