package com.DailyMelody.exception;

/**
 * @Author: DingXiaoyu
 * @Date: 0:26 2023/11/26
 * 你可以在这里自定义Exception
*/
public class DailyMelodyException extends RuntimeException{

    public DailyMelodyException(String message){
        super(message);
    }
    public static DailyMelodyException phoneAlreadyExists(){
        return new DailyMelodyException("手机号已经存在!");
    }

    public static DailyMelodyException notLogin(){
        return new DailyMelodyException("未登录!");
    }

    public static DailyMelodyException phoneOrPasswordError(){
        return new DailyMelodyException("手机号或密码错误!");
    }

    public static DailyMelodyException fileUploadFail(){
        return new DailyMelodyException("文件上传失败!");
    }

    public static DailyMelodyException nameAlreadyExists(){
        return new DailyMelodyException("名称已经存在!");
    }

    public static DailyMelodyException storeNotExists(){
        return new DailyMelodyException("店铺不存在!");
    }

    public static DailyMelodyException productNotExists(){
        return new DailyMelodyException("商品不存在!");
    }

    public static DailyMelodyException orderNotExists(){
        return new DailyMelodyException("订单不存在！");
    }

    public static DailyMelodyException orderStatusError(){
        return new DailyMelodyException("订单状态错误！");
    }

    public static DailyMelodyException stockNotEnough(){
        return new DailyMelodyException("库存不足！");
    }

    public static DailyMelodyException fullReductionCouponError(){
        return new DailyMelodyException("满减券设置错误！");
    }

    public static DailyMelodyException couponNotAllowed(){
        return new DailyMelodyException("该优惠券不支持使用！");
    }

    public static DailyMelodyException couponGroupNotEnough(){
        return new DailyMelodyException("优惠券数量不足！");
    }

    public static DailyMelodyException payError(){
        return new DailyMelodyException("支付失败！");
    }
}
