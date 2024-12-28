package com.DailyMelody.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.DailyMelody.enums.OrderStatusEnum;
import com.DailyMelody.enums.OrderTypeEnum;
import com.DailyMelody.po.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderVO {
    
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer amount;

    private Double paid;

    private OrderTypeEnum type;

    private String content;

    private Integer rating;

    private OrderStatusEnum status;

    private Date createTime;

    private Date finishTime;

    private String productName;

    private Integer storeId;

    private Double price;

    public Order toPO(){
        Order order=new Order();
        order.setAmount(this.amount);
        order.setContent(this.content);
        order.setCreateTime(this.createTime);
        order.setFinishTime(this.finishTime);
        order.setId(this.id);
        order.setPaid(this.paid);
        order.setProductId(this.productId);
        order.setRating(this.rating);
        order.setStatus(this.status);
        order.setType(this.type);
        order.setUserId(this.userId);
        return order;
    }

    public List<String> getHeaderList() {
        return new ArrayList<String>() {
            {
                add("订单编号");
                add("用户编号");
                add("商品编号");
                add("商品数量");
                add("付款额");
                add("订单类型");
                add("订单评价");
                add("订单评分");
                add("订单状态");
                add("订单创建时间");
                add("订单完成时间");
                add("商品名称");
                add("商店编号");
                add("订单金额");
            }
        };
    }

    public List<Object> getCellContent() {
        List<Object> content = new ArrayList<>();
        content.add(this.id);
        content.add(this.userId);
        content.add(this.productId);
        content.add(this.amount);
        content.add(this.paid);
        content.add(this.type);
        content.add(this.content);
        content.add(this.rating);
        content.add(this.status);
        content.add(this.createTime);
        content.add(this.finishTime);
        content.add(this.productName);
        content.add(this.storeId);
        content.add(this.price);
        return content;
    }
}
