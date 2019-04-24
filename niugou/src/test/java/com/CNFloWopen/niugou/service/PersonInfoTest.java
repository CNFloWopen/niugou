package com.CNFloWopen.niugou.service;

import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.entity.WechatAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoTest {
    @Autowired
    PersonInfoService personInfoService;
    @Test
    public void testProsonInfogetBuId()
    {
        WechatAuth wechatAuth = new WechatAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(8L);
        wechatAuth.setPersonInfo(personInfo);
        personInfo = personInfoService.getPersonInfoById(wechatAuth.getPersonInfo().getUserId());
    }
}
