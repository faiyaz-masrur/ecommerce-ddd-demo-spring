package com.example.EcommerceDddDemoJava.modules.ordering.application.models;

import java.math.BigDecimal;

public record PlaceOrderRequest(
        String customerId,
        String sku,
        int quantity,
        BigDecimal unitPrice) {
}
