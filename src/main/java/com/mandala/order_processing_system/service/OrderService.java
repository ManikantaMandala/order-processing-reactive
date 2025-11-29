package com.mandala.order_processing_system.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.mandala.order_processing_system.dto.OrderDtoRequest;
import com.mandala.order_processing_system.dto.OrderDtoResponse;
import com.mandala.order_processing_system.enums.Stage;
import com.mandala.order_processing_system.mapper.DtoMapper;
import com.mandala.order_processing_system.model.Order;
import com.mandala.order_processing_system.model.Product;
import com.mandala.order_processing_system.repository.OrderRepository;
import com.mandala.order_processing_system.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
	}

	public Mono<OrderDtoResponse> saveOrder(OrderDtoRequest orderDto) {
		List<Long> productIdList = orderDto.getProductIdList();
		Order order = DtoMapper.mapToDao(orderDto);
		List<Product> products = new ArrayList<>();

		productIdList.stream()
				.map(id -> productRepository
						.findById(id)
						.subscribe(product -> products.add(product)));
		order.setItems(products);
		return orderRepository.save(order)
				.map(o -> DtoMapper.mapToDto(o))
				.doOnSuccess(ord -> statusUpdater(ord, order))
				.onErrorResume(
						err -> Mono.error(err));
	}

	public void statusUpdater(OrderDtoResponse orderDtoResponse, Order order) {
		Flux.just(
				Stage.RECIEVED, Stage.PROCESSING, Stage.PROCESSED,
				Stage.PACKAGING, Stage.PACKAGED, Stage.SHIPPING,
				Stage.SHIPPMENT_DONE)
				.delayElements(Duration.ofSeconds(2))
				.subscribe(stage -> {
					order.setStage(stage);
					orderRepository.save(order);
				});
	}

	public Mono<OrderDtoResponse> getOrder(Long id) {
		return orderRepository.findById(id)
				.map(order -> DtoMapper.mapToDto(order));
	}

	public Flux<OrderDtoResponse> getOrders() {
		return orderRepository.findAll()
				.map(order -> DtoMapper.mapToDto(order));
	}
}
