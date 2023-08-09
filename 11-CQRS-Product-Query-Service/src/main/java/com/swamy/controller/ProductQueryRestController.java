package com.swamy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.ProductDto;
import com.swamy.service.ProductQueryService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductQueryRestController {

	private ProductQueryService productQueryService;

	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		return new ResponseEntity<>(productQueryService.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Integer productId) {
		return new ResponseEntity<>(productQueryService.getProductById(productId), HttpStatus.OK);
	}
}
