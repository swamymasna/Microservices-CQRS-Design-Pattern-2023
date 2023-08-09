package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.swamy.dto.ProductDto;
import com.swamy.dto.ProductEvent;
import com.swamy.entity.Product;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.ProductRepository;
import com.swamy.service.ProductQueryService;
import static com.swamy.utils.AppConstants.*;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

	private ProductRepository productRepository;

	private ModelMapper modelMapper;

	@Override
	public List<ProductDto> getAllProducts() {

		List<Product> products = productRepository.findAll();

		return products.stream().map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public ProductDto getProductById(Integer productId) {

		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND, productId)));

		return modelMapper.map(existingProduct, ProductDto.class);
	}

	@KafkaListener(topics = TOPIC, groupId = GROUP_ID)
	public void processProductEvent(ProductEvent productEvent) {

		Product product = productEvent.getProduct();

		String eventType = productEvent.getEventType();

		if (eventType.equals(CREATE_PRODUCT)) {
			productRepository.save(product);
		}

		if (eventType.equals(UPDATE_PRODUCT)) {
			Product existingProduct = productRepository.findById(product.getProductId()).get();
			existingProduct.setProductName(product.getProductName());
			existingProduct.setProductPrice(product.getProductPrice());
			existingProduct.setProductDescription(product.getProductDescription());

			productRepository.save(existingProduct);
		}

		if (eventType.equals(DELETE_PRODUCT)) {
			Product existingProduct = productRepository.findById(product.getProductId()).get();

			productRepository.delete(existingProduct);
		}

	}
}
