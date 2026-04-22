package com.example.EcommerceDddDemoJava.modules.ordering.application.useCases;

import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderRequest;
import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderResult;
import com.example.EcommerceDddDemoJava.modules.ordering.application.services.OrderApplicationService;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PlaceOrderUseCase {

    private final OrderApplicationService orderApplicationService;

    public CompletableFuture<PlaceOrderResult> execute(PlaceOrderRequest request) {
        return CompletableFuture.supplyAsync(() -> {

            TraceHelper.logInfoAsync(PlaceOrderUseCase.class.getSimpleName(), "execution started").join();
            TraceHelper.logInfoAsync(PlaceOrderUseCase.class.getSimpleName(), "delegating to OrderApplicationService").join();

            PlaceOrderResult result = orderApplicationService.placeOrder(request).join();

            TraceHelper.logInfoAsync(PlaceOrderUseCase.class.getSimpleName(), "execution finished").join();
            return result;
        });
    }
}