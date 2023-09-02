package com.mygroup.huongtt.dto;

import com.mygroup.huongtt.model.Product;

public class OrderProductDto {

  public OrderProductDto(Product product, Integer quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  private Product product;
  private Integer quantity;

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
