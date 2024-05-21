package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    List<Coupon> findByUserId(Integer userId);

    List<Coupon> findByUserIdAndUsed(Integer userId, Boolean used);


}
