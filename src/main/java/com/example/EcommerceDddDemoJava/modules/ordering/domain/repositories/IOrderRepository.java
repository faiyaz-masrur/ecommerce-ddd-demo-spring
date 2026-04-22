package com.example.EcommerceDddDemoJava.modules.ordering.domain.repositories;

import com.example.EcommerceDddDemoJava.modules.ordering.domain.aggregates.OrderAggregate;

import java.util.concurrent.CompletableFuture;

public interface IOrderRepository {
    CompletableFuture<Void> save(OrderAggregate order);
}