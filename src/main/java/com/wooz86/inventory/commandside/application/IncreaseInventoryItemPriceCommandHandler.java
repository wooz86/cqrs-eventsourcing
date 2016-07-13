package com.wooz86.inventory.commandside.application;

import com.wooz86.api.IncreaseInventoryItemPriceCommand;
import com.wooz86.framework.application.CommandHandler;
import com.wooz86.framework.domain.Repository;
import com.wooz86.framework.eventstore.AggregateNotFoundException;
import com.wooz86.framework.eventstore.ConcurrencyException;
import com.wooz86.inventory.commandside.domain.model.InventoryItem;

import java.io.IOException;

public class IncreaseInventoryItemPriceCommandHandler implements CommandHandler<IncreaseInventoryItemPriceCommand> {

    private final Repository repository;

    public IncreaseInventoryItemPriceCommandHandler(Repository repository) {
        this.repository = repository;
    }

    public void handle(IncreaseInventoryItemPriceCommand command) throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, ConcurrencyException, AggregateNotFoundException {

        // Get from repository
        InventoryItem inventoryItem = repository.getById(command.getAggregateId());
        long originatingVersion = inventoryItem.getVersion();

        // Apply command
        inventoryItem.increasePrice(command.getPriceIncrease());

        // Persist generated events
        repository.save(inventoryItem, originatingVersion);

        inventoryItem.markEventsAsCommitted();
    }
}
