package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.vo.CouponGroupVO;
import com.seecoder.BlueWhale.vo.CouponVO;

import java.util.List;

public interface CouponService {

    Boolean createCouponGroup(CouponGroupVO couponGroupVO);

    List<CouponGroupVO> getCouponGroups();

    Boolean receiveCoupon(Integer couponGroupId);

    List<CouponVO> getCoupons();

    List<CouponVO> getCouponsWithStoreId(Integer storeId);
}
