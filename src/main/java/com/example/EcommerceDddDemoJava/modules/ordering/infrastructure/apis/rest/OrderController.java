package com.example.EcommerceDddDemoJava.modules.ordering.infrastructure.apis.rest;

import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderRequest;
import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderResult;
import com.example.EcommerceDddDemoJava.modules.ordering.application.useCases.PlaceOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;

    @PostMapping
    public ResponseEntity<PlaceOrderResult> placeOrder(@RequestBody PlaceOrderRequest request) {
        System.out.println("====================================================");
        System.out.println("DDD Demo - Place Order Flow");
        System.out.println("OrderController -> starting place order");
        System.out.println("====================================================");

        PlaceOrderResult result = placeOrderUseCase.execute(request);

        System.out.println("====================================================");
        System.out.println("OrderController -> flow finished");
        System.out.println("Order Id     : " + result.orderId());
        System.out.println("Order Status : " + result.orderStatus());
        System.out.println("Reserved SKU : " + result.sku());
        System.out.println("Quantity     : " + result.quantity());
        System.out.println("====================================================");

        return ResponseEntity.ok(result);
    }
}