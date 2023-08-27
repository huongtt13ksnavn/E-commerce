package com.mygroup.huongtt.repository;

import org.springframework.data.repository.CrudRepository;

import com.mygroup.huongtt.model.OrderProduct;
import com.mygroup.huongtt.model.OrderProductPK;

public interface OrderProductRepository
    extends CrudRepository<OrderProduct, OrderProductPK> {
}
