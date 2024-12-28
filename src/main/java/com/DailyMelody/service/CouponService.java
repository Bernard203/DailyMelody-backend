package com.DailyMelody.service;

import com.DailyMelody.vo.CouponGroupVO;
import com.DailyMelody.vo.CouponVO;

import java.util.List;

public interface CouponService {

    Boolean createCouponGroup(CouponGroupVO couponGroupVO);

    List<CouponGroupVO> getCouponGroups();

    Boolean receiveCoupon(Integer couponGroupId);

    List<CouponVO> getCoupons();

    List<CouponVO> getCouponsWithStoreId(Integer storeId);
}
