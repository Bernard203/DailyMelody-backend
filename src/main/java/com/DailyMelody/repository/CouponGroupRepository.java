package com.DailyMelody.repository;

import com.DailyMelody.po.CouponGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponGroupRepository extends JpaRepository<CouponGroup, Integer> {
    List<CouponGroup> findByStoreId(Integer storeId);

    List<CouponGroup> findByRestGreaterThan(Integer rest);


}
