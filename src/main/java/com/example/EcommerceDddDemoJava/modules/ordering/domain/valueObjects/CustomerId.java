package com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class CustomerId {

    private final String value;

    public static CustomerId create(String value) {
        TraceHelper.logInfo("CustomerId", "create('" + value + "')");
        if (value == null || value.isBlank()) {
            TraceHelper.logError("CustomerId", "value is null or blank");
            throw new IllegalArgumentException("CustomerId is required.");
        }
        return new CustomerId(value.trim());
    }
}
