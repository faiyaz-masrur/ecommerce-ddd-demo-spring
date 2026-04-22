package com.example.EcommerceDddDemoJava.modules.inventory.domain.repositories;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.aggregates.InventoryAggregate;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;

public interface IInventoryRepository {

    InventoryAggregate getBySku(Sku sku);
    void save(InventoryAggregate inventory);
}
