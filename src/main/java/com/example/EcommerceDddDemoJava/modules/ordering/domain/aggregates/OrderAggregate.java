package com.example.EcommerceDddDemoJava.modules.ordering.domain.aggregates;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Quantity;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.entities.OrderItems;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.CustomerId;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.Money;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.OrderId;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.OrderStatus;
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
public class OrderAggregate {

    private final List<OrderItems> items = new ArrayList<>();

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private OrderId id;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private CustomerId customerId;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private OrderStatus status;

    public List<OrderItems> getOrderLines() {
        return Collections.unmodifiableList(items);
    }

    public static CompletableFuture<OrderAggregate> createDraft(CustomerId customerId) {
        OrderAggregate order = new OrderAggregate();
        return TraceHelper.logInfoAsync("OrderAggregate", "createDraft()")
                .thenCompose(v -> OrderId.create())
                .thenCompose(orderId -> {
                    order.setId(orderId);
                    order.setCustomerId(customerId);
                    return OrderStatus.draft();
                })
                .thenApply(status -> {
                    order.setStatus(status);
                    return order;
                });
    }

    public CompletableFuture<Void> place(Sku sku, Quantity quantity, Money unitPrice) {
        return TraceHelper.logInfoAsync("OrderAggregate", "place()")
                .thenCompose(v -> {
                    if (!items.isEmpty()) {
                        return TraceHelper.logErrorAsync("OrderAggregate", "order already has items")
                                .thenApply(ignored -> {
                                    throw new IllegalStateException("Order already has items for this demo scenario.");
                                });
                    }
                    return OrderItems.create(sku, quantity, unitPrice);
                })
                .thenCompose(item -> {
                    items.add(item);
                    return OrderStatus.placed();
                })
                .thenCompose(status -> {
                    this.status = status;
                    return TraceHelper.logInfoAsync("OrderAggregate", "place() finished");
                });
    }
}