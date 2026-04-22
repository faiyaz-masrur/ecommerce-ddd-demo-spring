package com.example.EcommerceDddDemoJava.modules.ordering.application.services;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.aggregates.InventoryAggregate;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.repositories.IInventoryRepository;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Quantity;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderRequest;
import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderResult;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.aggregates.OrderAggregate;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.repositories.IOrderRepository;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.CustomerId;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.Money;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final IOrderRepository orderRepository;
    private final IInventoryRepository inventoryRepository;

    public PlaceOrderResult placeOrder(PlaceOrderRequest request) {
        TraceHelper.logInfo(OrderApplicationService.class.getSimpleName(), "placeOrder started");
        TraceHelper.logInfo(OrderApplicationService.class.getSimpleName(), "creating ValueObjects");

        CustomerId customerId = CustomerId.create(request.customerId());
        Sku sku = Sku.create(request.sku());
        Quantity quantity = Quantity.create(request.quantity());
        Money money = Money.create(request.unitPrice());

        TraceHelper.logInfo(OrderApplicationService.class.getSimpleName(), "loading InventoryAggregate from repository");
        InventoryAggregate inventory = inventoryRepository.getBySku(sku);

        TraceHelper.logInfo(OrderApplicationService.class.getSimpleName(), "creating OrderAggregate");
        OrderAggregate order = OrderAggregate.createDraft(customerId);

        TraceHelper.logInfo(OrderApplicationService.class.getSimpleName(), "calling OrderAggregate.place");
        order.place(sku, quantity, money);

        TraceHelper.logInfo(OrderApplicationService.class.getSimpleName(), "calling InventoryAggregate.reserve");
        inventory.reserve(order.getId(), sku, quantity);

        TraceHelper.logInfo(OrderApplicationService.class.getSimpleName(), "saving aggregates through repositories");
        orderRepository.save(order);
        inventoryRepository.save(inventory);

        TraceHelper.logInfo(OrderApplicationService.class.getSimpleName(), "building result model");
        return new PlaceOrderResult(
                order.getId().getValue(),
                order.getStatus().getValue(),
                sku.getValue(),
                quantity.getValue());
    }
}