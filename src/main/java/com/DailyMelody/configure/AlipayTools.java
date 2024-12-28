package com.DailyMelody.configure;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.DailyMelody.exception.DailyMelodyException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Getter
@Setter
public class AlipayTools {

    private String serverUrl;

    private String appId;

    private String appPrivateKey;

    private String alipayPublicKey;

    private String notifyUrl;

    private static String format="json";

    private static String charset="utf-8";

    private static String signType="RSA2";

    /**
     * @Author: DingXiaoyu
     * @Date: 11:25 2024/1/31
     * 使用支付宝沙箱
    */
    public String pay(String tradeName, String name , Double price){
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl,appId,appPrivateKey,format,charset,alipayPublicKey,signType);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", tradeName); //订单id-优惠券id
        bizContent.put("total_amount", price);
        bizContent.put("subject", name);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        String form;
        try  {
            form = alipayClient.pageExecute(request).getBody();
        }  catch  (Exception e) {
            throw DailyMelodyException.payError();
        }
        return form;
    }
}