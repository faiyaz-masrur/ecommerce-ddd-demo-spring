package com.example.EcommerceDddDemoJava.modules.inventory.infrastructure.repositories;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.aggregates.InventoryAggregate;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.repositories.IInventoryRepository;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public class InMemoryInventoryRepository implements IInventoryRepository {

    @Override
    public CompletableFuture<InventoryAggregate> getBySku(Sku sku) {
        return TraceHelper.logInfoAsync(InMemoryInventoryRepository.class.getSimpleName(), "getBySku()")
                .thenCompose(v -> InventoryAggregate.create(sku, 10));
    }

    @Override
    public CompletableFuture<Void> save(InventoryAggregate inventory) {
        return TraceHelper.logInfoAsync(InMemoryInventoryRepository.class.getSimpleName(), "save()");
    }
}