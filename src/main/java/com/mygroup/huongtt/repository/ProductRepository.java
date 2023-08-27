package com.mygroup.huongtt.repository;

import org.springframework.data.repository.CrudRepository;

import com.mygroup.huongtt.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
