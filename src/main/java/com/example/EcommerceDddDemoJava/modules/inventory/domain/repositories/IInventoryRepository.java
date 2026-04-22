package com.example.EcommerceDddDemoJava.modules.inventory.domain.repositories;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.aggregates.InventoryAggregate;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;

import java.util.concurrent.CompletableFuture;

public interface IInventoryRepository {

    CompletableFuture<InventoryAggregate> getBySku(Sku sku);
    CompletableFuture<Void> save(InventoryAggregate inventory);
}