package com.seecoder.BlueWhale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seecoder.BlueWhale.po.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);

    List<Order> findByProductIdAndFinishTimeNotNull(Integer productId);


}
