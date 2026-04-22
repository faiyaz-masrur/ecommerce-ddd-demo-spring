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
import java.util.concurrent.CompletableFuture;

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

    public static CompletableFuture<InventoryAggregate> create(Sku sku, int availableQuantity) {
        return TraceHelper.logInfoAsync("InventoryAggregate", "create()")
                .thenApply(v -> {
                    if (availableQuantity < 0)
                        throw new IllegalArgumentException("availableQuantity must be >= 0");
                    InventoryAggregate aggregate = new InventoryAggregate();
                    aggregate.setSku(sku);
                    aggregate.setAvailableQuantity(availableQuantity);
                    return aggregate;
                });
    }

    public CompletableFuture<Void> reserve(OrderId orderId, Sku sku, Quantity quantity) {
        return TraceHelper.logInfoAsync("InventoryAggregate", "reserve() started")
                .thenCompose(v -> {
                    if (!this.sku.getValue().equalsIgnoreCase(sku.getValue()))
                        throw new IllegalStateException("SKU mismatch.");
                    if (this.availableQuantity < quantity.getValue())
                        throw new IllegalStateException("Not enough stock.");
                    return StockReservation.create(orderId, sku, quantity);
                })
                .thenCompose(reservation -> {
                    reservations.add(reservation);
                    this.availableQuantity -= quantity.getValue();
                    return TraceHelper.logInfoAsync("InventoryAggregate", "reserve() finished");
                });
    }
}