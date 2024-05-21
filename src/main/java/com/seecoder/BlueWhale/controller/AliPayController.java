package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.po.Coupon;
import com.seecoder.BlueWhale.po.Order;
import com.seecoder.BlueWhale.repository.CouponRepository;
import com.seecoder.BlueWhale.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ali")
public class AliPayController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CouponRepository couponRepository;

    @PostMapping("/notify")
    public void notify(HttpServletRequest httpServletRequest){
        if (httpServletRequest.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, httpServletRequest.getParameter(name));
            }
            String tradeName = params.get("out_trade_no");
            String[] args=tradeName.split("-");
            Double paid = Double.parseDouble(params.get("total_amount"));
            Order order = orderRepository.findById(Integer.parseInt(args[0])).get();
            order.setPaid(paid);
            order.setStatus(OrderStatusEnum.UNSEND);
            orderRepository.save(order);
            if (!args[1].equals("$")){
                Coupon coupon=couponRepository.findById(Integer.parseInt(args[1])).get();
                coupon.setUsed(true);
                couponRepository.save(coupon);
            }
        }
    }

}
