package com.swamy.service.impl;

import static com.swamy.utils.AppConstants.CREATE_PRODUCT;
import static com.swamy.utils.AppConstants.DELETE_PRODUCT;
import static com.swamy.utils.AppConstants.PRODUCT_DELETION_SUCCEEDED;
import static com.swamy.utils.AppConstants.PRODUCT_NOT_FOUND;
import static com.swamy.utils.AppConstants.TOPIC;
import static com.swamy.utils.AppConstants.UPDATE_PRODUCT;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.swamy.dto.ProductDto;
import com.swamy.dto.ProductEvent;
import com.swamy.entity.Product;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.ProductRepository;
import com.swamy.service.ProductCommandService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductCommandServiceImpl implements ProductCommandService {

	private ProductRepository productRepository;

	private KafkaTemplate<String, ProductEvent> kafkaTemplate;

	private ModelMapper modelMapper;

	@Override
	public ProductDto saveProduct(ProductEvent productEvent) {

		Product product = productEvent.getProduct();

		Product savedProduct = productRepository.save(product);

		ProductEvent event = new ProductEvent();
		event.setEventType(CREATE_PRODUCT);
		event.setProduct(savedProduct);

		kafkaTemplate.send(TOPIC, event);

		return modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(Integer productId, ProductEvent productEvent) {

		Product newProduct = productEvent.getProduct();

		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND, productId)));

		existingProduct.setProductName(newProduct.getProductName());
		existingProduct.setProductPrice(newProduct.getProductPrice());
		existingProduct.setProductDescription(newProduct.getProductDescription());

		Product updatedProduct = productRepository.save(existingProduct);

		ProductEvent event = new ProductEvent();
		event.setEventType(UPDATE_PRODUCT);
		event.setProduct(updatedProduct);

		kafkaTemplate.send(TOPIC, event);

		return modelMapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public String deleteProduct(Integer productId) {

		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND, productId)));

		productRepository.delete(existingProduct);

		ProductEvent productEvent = new ProductEvent();
		productEvent.setEventType(DELETE_PRODUCT);
		productEvent.setProduct(existingProduct);

		kafkaTemplate.send(TOPIC, productEvent);

		return PRODUCT_DELETION_SUCCEEDED + productId;
	}
}
