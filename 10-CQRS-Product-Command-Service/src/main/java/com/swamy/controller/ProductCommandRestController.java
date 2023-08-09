package com.swamy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.ProductDto;
import com.swamy.dto.ProductEvent;
import com.swamy.service.ProductCommandService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductCommandRestController {

	private ProductCommandService productCommandService;

	@PostMapping
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductEvent productEvent) {
		return new ResponseEntity<>(productCommandService.saveProduct(productEvent), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Integer productId,
			@RequestBody ProductEvent productEvent) {
		return new ResponseEntity<>(productCommandService.updateProduct(productId, productEvent), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer productId) {
		return new ResponseEntity<>(productCommandService.deleteProduct(productId), HttpStatus.OK);
	}
}
