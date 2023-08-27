package com.mygroup.huongtt.repository;

import org.springframework.data.repository.CrudRepository;

import com.mygroup.huongtt.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
