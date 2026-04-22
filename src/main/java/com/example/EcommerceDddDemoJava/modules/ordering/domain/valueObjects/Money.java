package com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Money {

    private final BigDecimal amount;

    public static Money create(BigDecimal amount) {
        TraceHelper.logInfo("Money", "create(" + amount + ")");

        if (amount == null) {
            TraceHelper.logError("Money", "amount is null");
            throw new IllegalArgumentException("Money amount cannot be null.");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            TraceHelper.logError("Money", "amount <= 0");
            throw new IllegalArgumentException("Money must be greater than zero.");
        }

        return new Money(amount);
    }
}
