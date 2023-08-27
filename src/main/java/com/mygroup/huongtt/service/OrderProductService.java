package com.mygroup.huongtt.service;

import org.springframework.validation.annotation.Validated;

import com.mygroup.huongtt.model.OrderProduct;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
public interface OrderProductService {

  OrderProduct create(
      @NotNull(message = "The products for order cannot be null.") @Valid OrderProduct orderProduct);
}
