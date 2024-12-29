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
}
