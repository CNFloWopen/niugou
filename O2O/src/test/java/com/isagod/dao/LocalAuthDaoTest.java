package com.isagod.dao;

import com.isagod.BaseTest;
import com.isagod.entity.LocalAuth;
import com.isagod.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {
    @Autowired LocalAuthDao localAuthDao;
    private static final String  username = "testusername";
    private static final String password = "12345";
    @Test
    public void testBqueryLocalByUserNameAndPwd() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username,password);
        assertEquals("测试",localAuth.getPersonInfo().getName());
        System.out.println(localAuth.getPersonInfo().getName());
    }

    @Test
    public void testCqueryLocalByUserId() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        assertEquals("测试",localAuth.getPersonInfo().getName());
    }

    @Test
    public void testAinsertLocalAuth() {
        //新增一条平台账号信息
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(2L);
        //给平台绑定一条账号信息
        localAuth.setPersonInfo(personInfo);
        //设置上用户名和密码
        localAuth.setUserName(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testDupdateLocalAuth() {
        Date date = new Date();

        int effectedNum = localAuthDao.updateLocalAuth(1L,username,password,"444",date);
//        assertEquals(1,effectedNum);
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(2L);
        System.out.println(localAuth.getPassword());
    }
}