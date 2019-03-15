package com.isagod.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.isagod.BaseTest;
import com.isagod.entity.PersonInfo;
import com.isagod.entity.WechatAuth;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatAuthDaoTest extends BaseTest {
	@Autowired
	private WechatAuthDao wechatAuthDao;

	@Test
	public void testAInsertWechatAuth() throws Exception {
		//新增一条微信账号
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		//给账号绑定上y用户信息
		wechatAuth.setPersonInfo(personInfo);
		//随意设置上openId
		wechatAuth.setOpenId("dafahizhfdhaih");
		wechatAuth.setCreateTime(new Date());
		int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryWechatAuthByOpenId() throws Exception {
		WechatAuth wechatAuth = wechatAuthDao
				.queryWechatInfoByOpenId("dafahizhfdhaih");
		assertEquals("测试", wechatAuth.getPersonInfo().getName());
	}

//	@Test
//	public void testDeleteWechatAuth() throws Exception {
//		WechatAuth wechatAuth = wechatAuthDao
//				.queryWechatInfoByOpenId("dafahizhfdhaih");
//		int effectedNum = wechatAuthDao.deleteWechatAuth(wechatAuth
//				.getWechatAuthId());
//		assertEquals(1, effectedNum);
//	}
}
