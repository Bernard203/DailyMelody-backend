package com.DailyMelody;

import com.DailyMelody.po.User;
import com.DailyMelody.util.TokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DailymelodyApplicationTests {

	@Autowired
	TokenUtil tokenUtil;

	@Test
	void contextLoads() {
		User user=new User();
		user.setId(1);
		user.setPassword("123456");
		System.out.println(tokenUtil.getToken(user));
	}

}
