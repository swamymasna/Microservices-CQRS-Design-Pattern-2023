package com.swamy.service;

import com.swamy.dto.ProductDto;
import com.swamy.dto.ProductEvent;

public interface ProductCommandService {

	ProductDto saveProduct(ProductEvent productEvent);

	ProductDto updateProduct(Integer productId, ProductEvent productEvent);

	String deleteProduct(Integer productId);
}
