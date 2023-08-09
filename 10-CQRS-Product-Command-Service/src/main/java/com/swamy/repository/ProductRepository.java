package com.swamy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swamy.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
