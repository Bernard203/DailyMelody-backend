package com.DailyMelody.po;

import com.DailyMelody.vo.CouponVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Coupon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "group_id")
    private Integer groupId;

    @Basic
    @Column(name = "used")
    private Boolean used;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    public CouponVO toVO(){
        CouponVO couponVO=new CouponVO();
        couponVO.setId(this.id);
        couponVO.setUsed(this.used);
        couponVO.setUserId(this.userId);
        couponVO.setGroupId(this.groupId);
        return couponVO;
    }
}
