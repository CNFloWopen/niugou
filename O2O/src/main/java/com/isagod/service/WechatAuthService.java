package com.isagod.service;

import com.isagod.dto.WechatAuthExecution;
import com.isagod.entity.WechatAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



public interface WechatAuthService {

	/**
	 * 通过openId查找平台对应的账号
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 注册平台的微信账号
	 * @param wechatAuth
	 *
	 * @return
	 * @throws RuntimeException
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws RuntimeException;

}
