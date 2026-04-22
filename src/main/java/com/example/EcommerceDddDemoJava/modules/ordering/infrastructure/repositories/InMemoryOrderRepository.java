package com.example.EcommerceDddDemoJava.modules.ordering.infrastructure.repositories;

import com.example.EcommerceDddDemoJava.modules.ordering.domain.aggregates.OrderAggregate;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.repositories.IOrderRepository;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryOrderRepository implements IOrderRepository {

    private final List<OrderAggregate> orders = new ArrayList<>();

    @Override
    public void save(OrderAggregate order) {
        TraceHelper.logInfo(InMemoryOrderRepository.class.getSimpleName(), "save()");
        orders.add(order);
    }
}
