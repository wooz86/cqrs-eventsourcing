package com.wooz86;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.UUID;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, ConcurrencyException, AggregateNotFoundException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

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
    }
}

