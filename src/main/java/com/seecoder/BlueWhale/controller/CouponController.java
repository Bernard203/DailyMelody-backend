package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.CouponService;
import com.seecoder.BlueWhale.vo.CouponGroupVO;
import com.seecoder.BlueWhale.vo.CouponVO;
import com.seecoder.BlueWhale.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    CouponService couponService;

    @PostMapping
    public ResultVO<Boolean> createCouponGroup(@RequestBody CouponGroupVO couponGroupVO){
        return ResultVO.buildSuccess(couponService.createCouponGroup(couponGroupVO));
    }

    @GetMapping("/group")
    public ResultVO<List<CouponGroupVO>> getCouponGroups(){
        return ResultVO.buildSuccess(couponService.getCouponGroups());
    }

    @PostMapping("/receive")
    public ResultVO<Boolean> receiveCoupon(Integer couponGroupId){
        return ResultVO.buildSuccess(couponService.receiveCoupon(couponGroupId));
    }

    @GetMapping
    public ResultVO<List<CouponVO>> getCoupons(){
        return ResultVO.buildSuccess(couponService.getCoupons());
    }

    @GetMapping("/available")
    public ResultVO<List<CouponVO>> getCouponsWithStoreId(@RequestParam("storeId")Integer storeId){
        return ResultVO.buildSuccess(couponService.getCouponsWithStoreId(storeId));
    }
}
