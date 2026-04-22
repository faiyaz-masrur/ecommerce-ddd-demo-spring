package com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderId {

    private final String value;

    public static CompletableFuture<OrderId> create() {
        return TraceHelper.logInfoAsync("OrderId", "creating new OrderId")
                .thenApply(v -> {
                    String raw = "ORD-" + UUID.randomUUID().toString().replace("-", "");
                    String value = raw.substring(0, 12).toUpperCase();
                    return new OrderId(value);
                });
    }
}