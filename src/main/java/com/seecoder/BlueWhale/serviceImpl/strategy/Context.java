package com.seecoder.BlueWhale.serviceImpl.strategy;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;

public class Context {
    private final Double price;

    private CalculateStrategy calculateStrategy;

    public Context(Double price, CouponTypeEnum couponTypeEnum, Integer satisfaction, Integer minus) {
        this.price = price;
        switch (couponTypeEnum){
            case FULL_REDUCTION:
                calculateStrategy=new FillReductionCouponCalculateStrategy(satisfaction,minus);
                break;
            case SPECIAL:
                calculateStrategy=new SpecialCouponCalculateStrategy();
                break;
        }
    }

    public Double calculate(){
        return calculateStrategy.calculate(this.price);
    }
}
