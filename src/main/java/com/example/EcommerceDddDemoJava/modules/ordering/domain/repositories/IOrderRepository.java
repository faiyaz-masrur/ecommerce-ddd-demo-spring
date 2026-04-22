package com.example.EcommerceDddDemoJava.modules.ordering.domain.repositories;

import com.example.EcommerceDddDemoJava.modules.ordering.domain.aggregates.OrderAggregate;

public interface IOrderRepository {
    void save(OrderAggregate order);
}