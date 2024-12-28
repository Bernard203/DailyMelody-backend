package com.DailyMelody.vo;

import com.DailyMelody.enums.CouponTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponVO {

    private Integer id;

    private Integer groupId;

    private CouponTypeEnum type;

    private Integer satisfaction;

    private Integer minus;

    private Integer storeId;

    private Boolean used;

    private Integer userId;

    private String storeName;

}
