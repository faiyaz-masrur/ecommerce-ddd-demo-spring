package com.example.EcommerceDddDemoJava.modules.inventory.domain.aggregates;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.entities.StockReservation;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Quantity;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.OrderId;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InventoryAggregate {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private Sku sku;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private int availableQuantity;

    private final List<StockReservation> reservations = new ArrayList<>();

    public List<StockReservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public static InventoryAggregate create(Sku sku, int availableQuantity) {
        TraceHelper.logInfo("InventoryAggregate", "create()");

        if (availableQuantity < 0)
            throw new IllegalArgumentException("availableQuantity must be >= 0");

        InventoryAggregate aggregate = new InventoryAggregate();
        aggregate.setSku(sku);
        aggregate.setAvailableQuantity(availableQuantity);
        return aggregate;
    }

    public void reserve(OrderId orderId, Sku sku, Quantity quantity) {
        TraceHelper.logInfo("InventoryAggregate", "reserve() started");

        if (!this.sku.getValue().equalsIgnoreCase(sku.getValue()))
            throw new IllegalStateException("SKU mismatch.");

        if (this.availableQuantity < quantity.getValue())
            throw new IllegalStateException("Not enough stock.");

        StockReservation reservation = StockReservation.create(orderId, sku, quantity);
        reservations.add(reservation);
        this.availableQuantity -= quantity.getValue();

        TraceHelper.logInfo("InventoryAggregate", "reserve() finished");
    }
}
