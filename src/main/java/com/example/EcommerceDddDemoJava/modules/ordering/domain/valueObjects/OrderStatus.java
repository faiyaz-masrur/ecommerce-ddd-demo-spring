package com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderStatus {

    private final String value;

    public static OrderStatus draft() {
        TraceHelper.logInfo("OrderStatus", "draft()");
        return new OrderStatus("DRAFT");
    }

    public static OrderStatus placed() {
        TraceHelper.logInfo("OrderStatus", "placed()");
        return new OrderStatus("PLACED");
    }
}