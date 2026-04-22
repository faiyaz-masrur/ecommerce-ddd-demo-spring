package com.example.EcommerceDddDemoJava.modules.ordering.application.useCases;

import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderRequest;
import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderResult;
import com.example.EcommerceDddDemoJava.modules.ordering.application.services.OrderApplicationService;
import com.example.EcommerceDddDemoJava.shared.tracing.TraceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceOrderUseCase {

    private final OrderApplicationService orderApplicationService;

    public PlaceOrderResult execute(PlaceOrderRequest request) {
        TraceHelper.logInfo(PlaceOrderUseCase.class.getSimpleName(), "execution started");
        TraceHelper.logInfo(PlaceOrderUseCase.class.getSimpleName(), "delegating to OrderApplicationService");

        PlaceOrderResult result = orderApplicationService.placeOrder(request);

        TraceHelper.logInfo(PlaceOrderUseCase.class.getSimpleName(), "execution finished");
        return result;
    }
}