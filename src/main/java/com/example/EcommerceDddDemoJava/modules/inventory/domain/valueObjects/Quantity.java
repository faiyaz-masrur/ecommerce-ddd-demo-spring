package com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Quantity {

    private final int value;

    public static CompletableFuture<Quantity> create(int value) {
        return TraceHelper.logInfoAsync("Quantity", "create(" + value + ")")
                .thenCompose(v -> {
                    if (value <= 0) {
                        return TraceHelper.logErrorAsync("Quantity", "value <= 0")
                                .thenApply(ignored -> {
                                    throw new IllegalArgumentException("Quantity must be greater than zero.");
                                });
                    }
                    return CompletableFuture.completedFuture(new Quantity(value));
                });
    }
}