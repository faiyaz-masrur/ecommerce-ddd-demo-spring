package com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Sku {

    private final String value;

    public static Sku create(String value) {
        TraceHelper.logInfo("Sku", "create('" + value + "')");

        if (value == null || value.isBlank()) {
            TraceHelper.logError("Sku", "value is null or blank");
            throw new IllegalArgumentException("Sku is required.");
        }

        return new Sku(value.trim().toUpperCase());
    }
}
