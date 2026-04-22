package com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Money {

    private final BigDecimal amount;

    public static CompletableFuture<Money> create(BigDecimal amount) {
        return TraceHelper.logInfoAsync("Money", "create(" + amount + ")")
                .thenCompose(v -> {
                    if (amount == null) {
                        return TraceHelper.logErrorAsync("Money", "amount is null")
                                .thenApply(ignored -> {
                                    throw new IllegalArgumentException("Money amount cannot be null.");
                                });
                    }
                    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                        return TraceHelper.logErrorAsync("Money", "amount <= 0")
                                .thenApply(ignored -> {
                                    throw new IllegalArgumentException("Money must be greater than zero.");
                                });
                    }
                    return CompletableFuture.completedFuture(new Money(amount));
                });
    }
}