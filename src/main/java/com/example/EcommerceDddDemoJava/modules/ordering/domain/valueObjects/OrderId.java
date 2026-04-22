package com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects;

import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderId {

    private final String value;

    public static OrderId create() {
        TraceHelper.logInfo("OrderId", "creating new OrderId");

        String raw = "ORD-" + UUID.randomUUID().toString().replace("-", "");
        String value = raw.substring(0, 12).toUpperCase();

        return new OrderId(value);
    }
}