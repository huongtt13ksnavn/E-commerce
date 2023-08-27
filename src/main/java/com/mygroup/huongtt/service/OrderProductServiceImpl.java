package com.mygroup.huongtt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mygroup.huongtt.model.OrderProduct;
import com.mygroup.huongtt.repository.OrderProductRepository;

@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

  private OrderProductRepository orderProductRepository;

  public OrderProductServiceImpl(
      OrderProductRepository orderProductRepository) {
    this.orderProductRepository = orderProductRepository;
  }

  @Override
  public OrderProduct create(OrderProduct orderProduct) {
    return this.orderProductRepository.save(orderProduct);
  }
}
