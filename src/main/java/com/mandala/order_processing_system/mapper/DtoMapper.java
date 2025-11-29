package com.mandala.order_processing_system.mapper;

import com.mandala.order_processing_system.dto.OrderDtoRequest;
import com.mandala.order_processing_system.dto.OrderDtoResponse;
import com.mandala.order_processing_system.model.Order;

public class DtoMapper {

    public static Order mapToDao(OrderDtoRequest orderDto) {
        return Order.builder()
                .build();
    }

    public static OrderDtoResponse mapToDto(Order order) {
        return OrderDtoResponse.builder()
                .id(order.getId())
                .productIdList(order.getItems()
                        .stream()
                        .map(o -> o.getId())
                        .toList())
                .build();
    }
}
