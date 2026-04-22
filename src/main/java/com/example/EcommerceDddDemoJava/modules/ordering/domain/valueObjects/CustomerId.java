package com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CustomerId {

    private final String value;

    public static CompletableFuture<CustomerId> create(String value) {
        return TraceHelper.logInfoAsync("CustomerId", "create('" + value + "')")
                .thenCompose(v -> {
                    if (value == null || value.isBlank()) {
                        return TraceHelper.logErrorAsync("CustomerId", "value is null or blank")
                                .thenApply(ignored -> {
                                    throw new IllegalArgumentException("CustomerId is required.");
                                });
                    }
                    return CompletableFuture.completedFuture(new CustomerId(value.trim()));
                });
    }
}