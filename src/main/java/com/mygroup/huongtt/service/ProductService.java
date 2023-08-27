package com.mygroup.huongtt.service;

import org.springframework.validation.annotation.Validated;

import com.mygroup.huongtt.model.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Validated
public interface ProductService {

  @NotNull
  Iterable<Product> getAllProducts();

  Product getProduct(@Min(value = 1L, message = "Invalid product ID.") long id);

  Product save(Product product);
}
