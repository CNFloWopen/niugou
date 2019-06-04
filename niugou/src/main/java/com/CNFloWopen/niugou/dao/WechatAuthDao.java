package com.CNFloWopen.niugou.dao;


import com.CNFloWopen.niugou.entity.WechatAuth;

public interface WechatAuthDao {
	/**
	 * 根据openId查询平台对应的账号
	 * @param openId
	 * @return
	 */
	WechatAuth queryWechatInfoByOpenId(String openId);

	/**
	 * 添加对应的微信账号
	 * @param wechatAuth
	 * @return
	 */
	int insertWechatAuth(WechatAuth wechatAuth);
//
//	/**
//	 * 删除
//	 * @param wechatAuthId
//	 * @return
//	 */
//	int deleteWechatAuth(Long wechatAuthId);
}
