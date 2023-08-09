package com.swamy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

	private Integer productId;

	private String productName;

	private Double productPrice;

	private String productDescription;
}
