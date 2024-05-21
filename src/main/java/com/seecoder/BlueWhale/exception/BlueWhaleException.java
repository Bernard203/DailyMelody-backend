package com.seecoder.BlueWhale.exception;

/**
 * @Author: DingXiaoyu
 * @Date: 0:26 2023/11/26
 * 你可以在这里自定义Exception
*/
public class BlueWhaleException extends RuntimeException{

    public BlueWhaleException(String message){
        super(message);
    }
    public static BlueWhaleException phoneAlreadyExists(){
        return new BlueWhaleException("手机号已经存在!");
    }

    public static BlueWhaleException notLogin(){
        return new BlueWhaleException("未登录!");
    }

    public static BlueWhaleException phoneOrPasswordError(){
        return new BlueWhaleException("手机号或密码错误!");
    }

    public static BlueWhaleException fileUploadFail(){
        return new BlueWhaleException("文件上传失败!");
    }

    public static BlueWhaleException nameAlreadyExists(){
        return new BlueWhaleException("名称已经存在!");
    }

    public static BlueWhaleException storeNotExists(){
        return new BlueWhaleException("店铺不存在!");
    }

    public static BlueWhaleException productNotExists(){
        return new BlueWhaleException("商品不存在!");
    }

    public static BlueWhaleException orderNotExists(){
        return new BlueWhaleException("订单不存在！");
    }

    public static BlueWhaleException orderStatusError(){
        return new BlueWhaleException("订单状态错误！");
    }

    public static BlueWhaleException stockNotEnough(){
        return new BlueWhaleException("库存不足！");
    }

    public static BlueWhaleException fullReductionCouponError(){
        return new BlueWhaleException("满减券设置错误！");
    }

    public static BlueWhaleException couponNotAllowed(){
        return new BlueWhaleException("该优惠券不支持使用！");
    }

    public static BlueWhaleException couponGroupNotEnough(){
        return new BlueWhaleException("优惠券数量不足！");
    }

    public static BlueWhaleException payError(){
        return new BlueWhaleException("支付失败！");
    }
}
