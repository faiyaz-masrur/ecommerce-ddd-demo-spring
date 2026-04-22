package com.example.EcommerceDddDemoJava.modules.inventory.infrastructure.repositories;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.aggregates.InventoryAggregate;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.repositories.IInventoryRepository;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryInventoryRepository implements IInventoryRepository {

    @Override
    public InventoryAggregate getBySku(Sku sku) {
        TraceHelper.logInfo(InMemoryInventoryRepository.class.getSimpleName(), "getBySku()");
        return InventoryAggregate.create(sku, 10);
    }

    @Override
    public void save(InventoryAggregate inventory) {
        TraceHelper.logInfo(InMemoryInventoryRepository.class.getSimpleName(), "save()");
    }
}
