package com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Sku {

    private final String value;

    public static CompletableFuture<Sku> create(String value) {
        return TraceHelper.logInfoAsync("Sku", "create('" + value + "')")
                .thenCompose(v -> {
                    if (value == null || value.isBlank()) {
                        return TraceHelper.logErrorAsync("Sku", "value is null or blank")
                                .thenApply(ignored -> {
                                    throw new IllegalArgumentException("Sku is required.");
                                });
                    }
                    return CompletableFuture.completedFuture(new Sku(value.trim().toUpperCase()));
                });
    }
}