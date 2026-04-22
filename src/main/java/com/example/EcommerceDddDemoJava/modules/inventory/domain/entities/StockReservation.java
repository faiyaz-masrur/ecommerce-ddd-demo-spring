package com.example.EcommerceDddDemoJava.modules.inventory.domain.entities;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Quantity;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.OrderId;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class StockReservation {

    private final OrderId orderId;
    private final Sku sku;
    private final Quantity quantity;

    public static CompletableFuture<StockReservation> create(OrderId orderId, Sku sku, Quantity quantity) {
        return TraceHelper.logInfoAsync("StockReservation", "create()")
                .thenApply(v -> new StockReservation(orderId, sku, quantity));
    }
}