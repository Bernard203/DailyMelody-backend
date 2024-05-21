package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.OrderService;
import com.seecoder.BlueWhale.vo.OrderVO;
import com.seecoder.BlueWhale.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResultVO<OrderVO> create(@RequestBody OrderVO orderVO){
        return ResultVO.buildSuccess(orderService.create(orderVO));
    }

    @GetMapping
    public ResultVO<List<OrderVO>> getAllOrders(){
        return ResultVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResultVO<OrderVO> getOrder(@PathVariable(value = "id")Integer id){
        return ResultVO.buildSuccess(orderService.getOrder(id));
    }

    @PostMapping("/pay")
    public ResultVO<String> pay(@RequestParam("orderId") Integer orderId,@RequestParam("couponId")Integer couponId){
        return ResultVO.buildSuccess(orderService.pay(orderId,couponId));
    }

    @PostMapping("/deliver")
    public ResultVO<Boolean> deliver(@RequestParam("orderId") Integer orderId){
        return ResultVO.buildSuccess(orderService.deliver(orderId));
    }

    @PostMapping("/get")
    public ResultVO<Boolean> get(@RequestParam("orderId") Integer orderId){
        return ResultVO.buildSuccess(orderService.get(orderId));
    }
    
    @PostMapping("/comment")
    public ResultVO<Boolean> comment(@RequestParam("orderId") Integer orderId, @RequestParam("comment") String comment, @RequestParam("rating") Integer rating){
        return ResultVO.buildSuccess(orderService.comment(orderId, comment, rating));
    }

    @GetMapping("/calculate")
    public ResultVO<Double> calculate(@RequestParam("orderId") Integer orderId,@RequestParam("couponId")Integer couponId){
        return ResultVO.buildSuccess(orderService.calculate(orderId,couponId));
    }
}
