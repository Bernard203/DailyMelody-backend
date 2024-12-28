package com.DailyMelody.vo;

import com.DailyMelody.po.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserVO {

    private Integer id;

    private String name;

    private String phone;

    private String password;

    private Date createTime;

    private String keyword;

    public User toPO(){
        User user=new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setPhone(this.phone);
        user.setPassword(this.password);
        user.setCreateTime(this.createTime);
        user.setKeyword(this.keyword);
        return user;
    }
}
