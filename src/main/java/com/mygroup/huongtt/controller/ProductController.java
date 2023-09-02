package com.mygroup.huongtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mygroup.huongtt.model.Product;
import com.mygroup.huongtt.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping(value = { "", "/" })
  public @NotNull Iterable<Product> getProducts() {
    return productService.getAllProducts();
  }
}
