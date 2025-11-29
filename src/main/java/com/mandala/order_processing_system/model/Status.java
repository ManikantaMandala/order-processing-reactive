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
public class Status {
	private Long id;
	private Long orderId;
	private List<Stage> history;
}
