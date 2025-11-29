package com.mandala.order_processing_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mandala.order_processing_system.dto.OrderDtoRequest;
import com.mandala.order_processing_system.dto.OrderDtoResponse;
import com.mandala.order_processing_system.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public Mono<OrderDtoResponse> createOrder(@RequestBody OrderDtoRequest order) {
		return orderService.saveOrder(order);
	}

	@GetMapping
	public Flux<OrderDtoResponse> getAll() {
		return orderService.getOrders()
				.onErrorResume(err -> Mono.error(err));
	}

	@GetMapping("/{id}")
	public Mono<OrderDtoResponse> getById(Long id) {
		return orderService.getOrder(id)
				.onErrorResume(err -> Mono.error(err));
	}
}
