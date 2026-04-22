package com.example.EcommerceDddDemoJava.modules.ordering.application.models;

public record PlaceOrderResult(
        String orderId,
        String orderStatus,
        String sku,
        int quantity) {
}
