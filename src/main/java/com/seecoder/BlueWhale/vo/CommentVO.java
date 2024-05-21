package com.seecoder.BlueWhale.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentVO {

    Integer userId;

    String userName;

    Integer orderId;

    Integer rating;

    String comment;

    Date time;
}
