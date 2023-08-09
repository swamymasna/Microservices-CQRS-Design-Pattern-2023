package com.swamy.service;

import java.util.List;

import com.swamy.dto.ProductDto;

public interface ProductQueryService {

	List<ProductDto> getAllProducts();

	ProductDto getProductById(Integer productId);
}
