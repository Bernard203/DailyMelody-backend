package com.DailyMelody.serviceImpl.strategy;

public class FillReductionCouponCalculateStrategy implements CalculateStrategy{

    private final Integer satisfaction;

    private final Integer minus;

    public FillReductionCouponCalculateStrategy(Integer satisfaction, Integer minus) {
        this.satisfaction = satisfaction;
        this.minus = minus;
    }

    @Override
    public Double calculate(Double price) {
        return price>=satisfaction?price-minus:price;
    }
}
