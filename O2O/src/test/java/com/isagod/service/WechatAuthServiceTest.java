package com.isagod.service;

import com.isagod.BaseTest;
import com.isagod.dto.WechatAuthExecution;
import com.isagod.entity.PersonInfo;
import com.isagod.entity.WechatAuth;
import com.isagod.enums.WechatAuthStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class WechatAuthServiceTest extends BaseTest {
    @Autowired
    private WechatAuthService wechatAuthService;
    @Test
    public void testRegister()
    {
        //新增一条微信账号
        WechatAuth wechatAuth = new WechatAuth();
        PersonInfo personInfo = new PersonInfo();
        String openId = "isagod";
        //给微信账号设置上用户信息，但不设置上用户Id
        //希望创建微信账号的自动创建用户信息
        personInfo.setCreateTime(new Date());
        personInfo.setName("测试一下");
        personInfo.setUserType(1);
        wechatAuth.setPersonInfo(personInfo);
        wechatAuth.setCreateTime(new Date());
        wechatAuth.setOpenId(openId);
        WechatAuthExecution wae = wechatAuthService.register(wechatAuth);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(),wae.getState());
        //通过openId找到新增wechatAuth
        wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
        System.out.println(wechatAuth.getPersonInfo().getName());
    }

}