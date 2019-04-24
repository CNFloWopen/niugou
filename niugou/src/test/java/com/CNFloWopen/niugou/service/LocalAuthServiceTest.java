package com.CNFloWopen.niugou.service;


import com.CNFloWopen.niugou.dto.LocalAuthExecution;
import com.CNFloWopen.niugou.entity.LocalAuth;
import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.enums.WechatAuthStateEnum;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthServiceTest {
    @Autowired
    private LocalAuthService localAuthService;
    @Test
    public void testABindLocalAuth()
    {
        //新增一条平台账号
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        String username = "pawww";
        String password = "testpaord";
        //给平台账号设置上用户信息
        //给用户设置上用户id，表明是某个用户创建的账号
        // 一个用户只能有一个本地账号
        personInfo.setUserId(3L);
        //给平台账号设置上用户信息，标明是与哪个用户绑定
        localAuth.setPersonInfo(personInfo);
        //设置账号
        localAuth.setUserName(username);
        //设置密码
        localAuth.setPassword(password);
        //绑定账号
        LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(),lae.getState());
        //通过uerId找到新增的localAuth
        localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());
        System.out.println("查询的用户名："+localAuth.getPersonInfo().getName());
        System.out.println("查询的用户密码："+localAuth.getPassword());
    }


    @Test
    public void testBModifeLoaclAuth()
    {
        //设置修改的账号信息
        //密码不能一样
        long userId = 4;
        String username = "pawww";
        String password = "hello";
        String newpassword = "you";
        //修改账号相对应的密码
        LocalAuthExecution lae = localAuthService.modifyLocalAuth(userId,username,password,newpassword);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(),lae.getState());
        //通过账号和密码找到修改后的localAuth
        LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(username,newpassword);
        //打印用户名字看看和预期是否相符
        System.out.println(localAuth.getPersonInfo().getName());
    }
}