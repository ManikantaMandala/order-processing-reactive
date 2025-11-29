package com.mandala.order_processing_system.model;

import java.util.List;

import com.mandala.order_processing_system.enums.Stage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	private Long id;
	private List<Product> items;
	private Double cost;
	private Stage stage;

	public void setItems(List<Product> products) {
		this.cost = calculateCost(products);
		this.items = products;
	}

	private Double calculateCost(List<Product> products) {
		return products.stream()
				.map(product -> product.getCost())
				.reduce(0.0, (p1, p2) -> p1 + p2);
	}
}
