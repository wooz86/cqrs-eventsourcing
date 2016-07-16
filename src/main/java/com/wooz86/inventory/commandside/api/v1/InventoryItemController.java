package com.wooz86.inventory.commandside.api.v1;

import com.wooz86.api.ChangeInventoryItemTitleCommand;
import com.wooz86.api.CreateInventoryItemCommand;
import com.wooz86.api.IncreaseInventoryItemPriceCommand;
import com.wooz86.api.ReduceInventoryItemPriceCommand;
import com.wooz86.framework.application.RepositoryImpl;
import com.wooz86.framework.domain.Repository;
import com.wooz86.framework.eventstore.AggregateNotFoundException;
import com.wooz86.framework.eventstore.ConcurrencyException;
import com.wooz86.inventory.commandside.application.ChangeInventoryItemTitleCommandHandler;
import com.wooz86.inventory.commandside.application.CreateInventoryItemCommandHandler;
import com.wooz86.inventory.commandside.application.IncreaseInventoryItemPriceCommandHandler;
import com.wooz86.inventory.commandside.application.ReduceInventoryItemPriceCommandHandler;
import com.wooz86.inventory.commandside.domain.model.InventoryItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
public class InventoryItemController {

    @RequestMapping("/api/v1/inventory-items")
    public String index() throws IllegalAccessException, ConcurrencyException, InstantiationException, AggregateNotFoundException, ClassNotFoundException, IOException {
        UUID aggregateId = UUID.randomUUID();

        Repository repository = new RepositoryImpl<>(InventoryItem.class);

        // Create the inventory item
        CreateInventoryItemCommandHandler createCommandHandler = new CreateInventoryItemCommandHandler(repository);
        CreateInventoryItemCommand createCommand = new CreateInventoryItemCommand(aggregateId, "First product", 100.25);
        createCommandHandler.handle(createCommand);

        // Change the title of the inventory item
        ChangeInventoryItemTitleCommand changeTitleCommand =
                new ChangeInventoryItemTitleCommand(aggregateId, "First product (awesome right?)");
        ChangeInventoryItemTitleCommandHandler changeTitleCommandHandler =
                new ChangeInventoryItemTitleCommandHandler(repository);
        changeTitleCommandHandler.handle(changeTitleCommand);

        // Increase the price of the inventory item
        IncreaseInventoryItemPriceCommand increasePriceCommand = new IncreaseInventoryItemPriceCommand(aggregateId, 100);
        IncreaseInventoryItemPriceCommandHandler increasePriceCommandHandler =
                new IncreaseInventoryItemPriceCommandHandler(repository);
        increasePriceCommandHandler.handle(increasePriceCommand);

        // Reduce the price of the inventory item
        ReduceInventoryItemPriceCommand reducePriceCommand = new ReduceInventoryItemPriceCommand(aggregateId, 22.5);
        ReduceInventoryItemPriceCommandHandler reducePriceCommandHandler = new ReduceInventoryItemPriceCommandHandler(repository);
        reducePriceCommandHandler.handle(reducePriceCommand);

        // Reduce the price of the inventory item
        ReduceInventoryItemPriceCommand reducePriceCommand2 = new ReduceInventoryItemPriceCommand(aggregateId, 122.23);
        reducePriceCommandHandler.handle(reducePriceCommand2);

        // Increase the price of the inventory item
        IncreaseInventoryItemPriceCommand increasePriceCommand2 =
                new IncreaseInventoryItemPriceCommand(aggregateId, 532);
        increasePriceCommandHandler.handle(increasePriceCommand2);

        // Try to build up state from above events
//        Repository repository = new RepositoryImpl<>(InventoryItem.class);
//
//        InventoryItem aggRoot = repository.getById(aggregateId);

        return "Commands processed successfully";
    }
}
