package com.DailyMelody.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DailyMelody.po.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);

    List<Order> findByProductIdAndFinishTimeNotNull(Integer productId);


}
