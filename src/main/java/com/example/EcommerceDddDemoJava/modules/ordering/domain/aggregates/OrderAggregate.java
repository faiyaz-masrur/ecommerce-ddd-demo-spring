package com.example.EcommerceDddDemoJava.modules.ordering.domain.aggregates;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Quantity;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.entities.OrderItems;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.CustomerId;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.Money;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.OrderId;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.OrderStatus;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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

    public static OrderAggregate createDraft(CustomerId customerId) {
        TraceHelper.logInfo("OrderAggregate", "createDraft()");

        OrderAggregate order = new OrderAggregate();
        order.setId(OrderId.create());
        order.setCustomerId(customerId);
        order.setStatus(OrderStatus.draft());

        return order;
    }

    public void place(Sku sku, Quantity quantity, Money unitPrice) {
        TraceHelper.logInfo("OrderAggregate", "place()");

        if (!items.isEmpty()) {
            TraceHelper.logError("OrderAggregate", "order already has items");
            throw new IllegalStateException("Order already has items for this demo scenario.");
        }

        OrderItems item = OrderItems.create(sku, quantity, unitPrice);
        items.add(item);

        this.status = OrderStatus.placed();

        TraceHelper.logInfo("OrderAggregate", "place() finished");
    }

}
