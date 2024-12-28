package com.DailyMelody.vo;

import com.DailyMelody.enums.RoleEnum;
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

    private Integer storeId;

    private String address;

    private RoleEnum role;

    private Date createTime;

    private String storeName;

    public User toPO(){
        User user=new User();
        user.setId(this.id);
        user.setAddress(this.address);
        user.setName(this.name);
        user.setPhone(this.phone);
        user.setRole(this.role);
        user.setStoreId(this.storeId);
        user.setPassword(this.password);
        user.setCreateTime(this.createTime);
        return user;
    }
}
