package com.DailyMelody.service;

import com.DailyMelody.vo.UserVO;

public interface UserService {
    Boolean register(UserVO userVO);

    String login(String phone,String password);

    UserVO getInformation();

    Boolean updateInformation(UserVO userVO);
}
