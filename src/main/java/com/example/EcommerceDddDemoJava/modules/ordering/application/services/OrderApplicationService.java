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

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final IOrderRepository orderRepository;
    private final IInventoryRepository inventoryRepository;

    public CompletableFuture<PlaceOrderResult> placeOrder(PlaceOrderRequest request) {
        return CompletableFuture.supplyAsync(() -> {

            TraceHelper.logInfoAsync(OrderApplicationService.class.getSimpleName(), "placeOrder started").join();
            TraceHelper.logInfoAsync(OrderApplicationService.class.getSimpleName(), "creating ValueObjects").join();

            CustomerId customerId = CustomerId.create(request.customerId()).join();
            Sku sku               = Sku.create(request.sku()).join();
            Quantity quantity      = Quantity.create(request.quantity()).join();
            Money money            = Money.create(request.unitPrice()).join();

            TraceHelper.logInfoAsync(OrderApplicationService.class.getSimpleName(), "loading InventoryAggregate from repository").join();
            InventoryAggregate inventory = inventoryRepository.getBySku(sku).join();

            TraceHelper.logInfoAsync(OrderApplicationService.class.getSimpleName(), "creating OrderAggregate").join();
            OrderAggregate order = OrderAggregate.createDraft(customerId).join();

            TraceHelper.logInfoAsync(OrderApplicationService.class.getSimpleName(), "calling OrderAggregate.place").join();
            order.place(sku, quantity, money).join();

            TraceHelper.logInfoAsync(OrderApplicationService.class.getSimpleName(), "calling InventoryAggregate.reserve").join();
            inventory.reserve(order.getId(), sku, quantity).join();

            TraceHelper.logInfoAsync(OrderApplicationService.class.getSimpleName(), "saving aggregates through repositories").join();
            orderRepository.save(order).join();
            inventoryRepository.save(inventory).join();

            TraceHelper.logInfoAsync(OrderApplicationService.class.getSimpleName(), "building result model").join();
            return new PlaceOrderResult(
                    order.getId().getValue(),
                    order.getStatus().getValue(),
                    sku.getValue(),
                    quantity.getValue());
        });
    }
}