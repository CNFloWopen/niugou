package com.CNFloWopen.niugou;

import com.CNFloWopen.niugou.dao.LocalAuthDao;
import com.CNFloWopen.niugou.entity.LocalAuth;
import com.CNFloWopen.niugou.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthDaoTest {
    @Autowired
    LocalAuthDao localAuthDao;
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
        assertEquals("CNFloWopen",localAuth.getPersonInfo().getName());
    }

    @Test
    public void testAinsertLocalAuth() {
        //新增一条平台账号信息
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(4L);
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