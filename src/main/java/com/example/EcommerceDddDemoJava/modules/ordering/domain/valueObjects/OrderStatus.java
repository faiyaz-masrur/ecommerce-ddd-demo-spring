package com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderStatus {

    private final String value;

    public static CompletableFuture<OrderStatus> draft() {
        return TraceHelper.logInfoAsync("OrderStatus", "draft()")
                .thenApply(v -> new OrderStatus("DRAFT"));
    }

    public static CompletableFuture<OrderStatus> placed() {
        return TraceHelper.logInfoAsync("OrderStatus", "placed()")
                .thenApply(v -> new OrderStatus("PLACED"));
    }
}