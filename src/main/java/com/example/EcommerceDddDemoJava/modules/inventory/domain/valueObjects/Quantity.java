package com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Quantity {

    private final int value;

    public static Quantity create(int value) {
        TraceHelper.logInfo("Quantity", "create(" + value + ")");

        if (value <= 0) {
            TraceHelper.logError("Quantity", "value <= 0");
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        return new Quantity(value);
    }
}
