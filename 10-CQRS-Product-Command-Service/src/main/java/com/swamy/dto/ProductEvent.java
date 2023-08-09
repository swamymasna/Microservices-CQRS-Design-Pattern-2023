package com.swamy.dto;

import com.swamy.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEvent {

	private String eventType;
	private Product product;
}
