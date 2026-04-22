package com.example.EcommerceDddDemoJava.modules.ordering.domain.entities;

import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Quantity;
import com.example.EcommerceDddDemoJava.modules.inventory.domain.valueObjects.Sku;
import com.example.EcommerceDddDemoJava.modules.ordering.domain.valueObjects.Money;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderItems {

    private Sku sku;
    private Quantity quantity;
    private Money unitPrice;

    public static OrderItems create(Sku sku, Quantity quantity, Money unitPrice) {
        TraceHelper.logInfo("OrderItems", "create()");
        return new OrderItems(sku, quantity, unitPrice);
    }
}