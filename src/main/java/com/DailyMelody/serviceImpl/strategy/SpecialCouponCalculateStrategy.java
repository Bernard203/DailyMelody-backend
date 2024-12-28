package com.DailyMelody.serviceImpl.strategy;

/**
 * @Author: DingXiaoyu
 * @Date: 22:35 2023/12/19
 * “蓝鲸券”使用规则：
 * 0-100元区间打九五折；
 * 100-200元区间打九折；
 * 200-300元区间打八五折；
 * 300-400元区间打八折；
 * 400-500元区间打七五折；
 * 500元以上区间打七折。
*/
public class SpecialCouponCalculateStrategy implements CalculateStrategy{

    static final double[] table={0,95,185,270,350,425};
    static final double[] discount={0.95,0.9,0.85,0.8,0.75,0.7};
    @Override
    public Double calculate(Double price) {
        int index=Math.min((int)(price/100),5);
        return table[index]+(price-100*index)*discount[index];
    }
}
