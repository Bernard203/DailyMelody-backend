package com.DailyMelody.vo;

import com.DailyMelody.enums.CouponTypeEnum;
import com.DailyMelody.po.CouponGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponGroupVO {

    private Integer id;

    private CouponTypeEnum type;

    private Integer satisfaction;

    private Integer minus;

    private Integer storeId;

    private Integer rest;

    private String storeName;

    public CouponGroup toPO(){
        CouponGroup couponGroup=new CouponGroup();
        couponGroup.setId(this.id);
        couponGroup.setRest(this.rest);
        couponGroup.setMinus(this.minus);
        couponGroup.setType(this.type);
        couponGroup.setSatisfaction(this.satisfaction);
        couponGroup.setStoreId(this.storeId);
        return couponGroup;
    }
}
