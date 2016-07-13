package com.wooz86.inventory.commandside.domain.model;


import com.wooz86.api.InventoryItemCreatedEvent;
import com.wooz86.api.InventoryItemPriceIncreasedEvent;
import com.wooz86.api.InventoryItemPriceReducedEvent;
import com.wooz86.api.InventoryItemTitleChangedEvent;
import com.wooz86.framework.domain.Event;
import com.wooz86.framework.domain.AbstractEventSourcedAggregateRoot;

import java.util.UUID;

public class InventoryItem extends AbstractEventSourcedAggregateRoot {

    private String title;
    private double price;

    public InventoryItem() {
    }

    public InventoryItem(UUID id, String title, double price) {
        apply((Event) new InventoryItemCreatedEvent(id, title, price));
    }

    public void changeTitle(String title) {
        apply((Event) new InventoryItemTitleChangedEvent(id, title));
    }

    public void increasePrice(double priceIncrease) {
        apply((Event) new InventoryItemPriceIncreasedEvent(id, priceIncrease));
    }

    public void reducePrice(double priceReduction) {
        apply((Event) new InventoryItemPriceReducedEvent(id, priceReduction));
    }

    @Override
    public void apply(Event event) {
        apply(event, true);
    }

    private void apply(Event event, boolean isNew) {
        if (event instanceof InventoryItemCreatedEvent) {
            apply((InventoryItemCreatedEvent) event);
        } else if (event instanceof InventoryItemTitleChangedEvent) {
            apply((InventoryItemTitleChangedEvent) event);
        } else if (event instanceof InventoryItemPriceIncreasedEvent) {
            apply((InventoryItemPriceIncreasedEvent) event);
        } else if (event instanceof InventoryItemPriceReducedEvent) {
            apply((InventoryItemPriceReducedEvent) event);
        }

        if (isNew) {
            uncommittedEvents.add(event);
        }

        incrementVersion();
    }

    private void apply(InventoryItemCreatedEvent event) {
        id = event.getAggregateId();
        title = event.getTitle();
        price = event.getPrice();
    }

    private void apply(InventoryItemTitleChangedEvent event) {
        title = event.getTitle();
    }

    private void apply(InventoryItemPriceIncreasedEvent event) {
        price += event.getPriceIncrease();
    }

    private void apply(InventoryItemPriceReducedEvent event) {
        price -= event.getPriceReduction();
    }

    @Override
    public void loadFromHistory(Iterable<Event> events) {
        for (Event event : events) {
            apply(event, false);
        }
    }
}
