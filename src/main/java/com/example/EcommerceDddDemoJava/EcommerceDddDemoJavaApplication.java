package com.example.EcommerceDddDemoJava;

import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderRequest;
import com.example.EcommerceDddDemoJava.modules.ordering.application.models.PlaceOrderResult;
import com.example.EcommerceDddDemoJava.modules.ordering.application.useCases.PlaceOrderUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.math.BigDecimal;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@RequiredArgsConstructor
public class EcommerceDddDemoJavaApplication implements CommandLineRunner {

	private final PlaceOrderUseCase placeOrderUseCase;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceDddDemoJavaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("====================================================");
		System.out.println("DDD Demo - Place Order Flow");
		System.out.println("EcommerceDddDemoJavaApplication -> starting application");
		System.out.println("====================================================");

		PlaceOrderRequest request = new PlaceOrderRequest(
				"CUST-1001",
				"SKU-RED-TSHIRT",
				2,
				new BigDecimal("499.00"));

		PlaceOrderResult result = placeOrderUseCase.execute(request);

		System.out.println("====================================================");
		System.out.println("EcommerceDddDemoJavaApplication -> flow finished");
		System.out.println("Order Id     : " + result.orderId());
		System.out.println("Order Status : " + result.orderStatus());
		System.out.println("Reserved SKU : " + result.sku());
		System.out.println("Quantity     : " + result.quantity());
		System.out.println("====================================================");
	}
}
