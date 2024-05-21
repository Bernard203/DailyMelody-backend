package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.vo.CouponGroupVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CouponGroup {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CouponTypeEnum type;

    @Basic
    @Column(name = "satisfaction")
    private Integer satisfaction;

    @Basic
    @Column(name = "minus")
    private Integer minus;

    @Basic
    @Column(name = "store_id")
    private Integer storeId;

    @Basic
    @Column(name = "rest")
    private Integer rest;

    public CouponGroupVO toVO(){
        CouponGroupVO couponGroupVO=new CouponGroupVO();
        couponGroupVO.setRest(this.rest);
        couponGroupVO.setId(this.id);
        couponGroupVO.setMinus(this.minus);
        couponGroupVO.setType(this.type);
        couponGroupVO.setSatisfaction(this.satisfaction);
        couponGroupVO.setStoreId(this.storeId);
        return couponGroupVO;
    }
}
