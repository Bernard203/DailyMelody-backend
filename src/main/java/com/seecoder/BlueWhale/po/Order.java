package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.enums.OrderTypeEnum;
import com.seecoder.BlueWhale.vo.OrderVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`order`") //order是数据库保留字段，使用要加引号
public class Order {
        
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "product_id")
    private Integer productId;

    @Basic
    @Column(name = "amount")
    private Integer amount;

    @Basic
    @Column(name = "paid")
    private Double paid;

    @Basic
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum type;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "rating")
    private Integer rating;

    @Basic
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "finish_time")
    private Date finishTime;

    public OrderVO toVO(){
        OrderVO orderVO=new OrderVO();
        orderVO.setAmount(this.amount);
        orderVO.setContent(this.content);
        orderVO.setCreateTime(this.createTime);
        orderVO.setFinishTime(this.finishTime);
        orderVO.setId(this.id);
        orderVO.setPaid(this.paid);
        orderVO.setProductId(this.productId);
        orderVO.setRating(this.rating);
        orderVO.setStatus(this.status);
        orderVO.setType(this.type);
        orderVO.setUserId(this.userId);
        return orderVO;
    }
}
