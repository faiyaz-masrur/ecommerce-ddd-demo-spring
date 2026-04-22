package com.example.EcommerceDddDemoJava.modules.ordering.domain.entities;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Quantity;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.Money;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderItems {

    private Sku sku;
    private Quantity quantity;
    private Money unitPrice;

    public static CompletableFuture<OrderItems> create(Sku sku, Quantity quantity, Money unitPrice) {
        return TraceHelper.logInfoAsync("OrderItems", "create()")
                .thenApply(v -> new OrderItems(sku, quantity, unitPrice));
    }
}