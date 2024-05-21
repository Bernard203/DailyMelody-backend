package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.vo.OrderVO;

import java.util.List;

public interface OrderService {
    OrderVO create(OrderVO orderVO);

    List<OrderVO> getAllOrders();

    OrderVO getOrder(Integer id);

    String pay(Integer orderId,Integer couponId);

    Boolean deliver(Integer orderId);

    Boolean get(Integer orderId);

    Boolean comment(Integer orderId, String comment, Integer rating);

    Double calculate(Integer orderId, Integer couponId);

}
