package com.CNFloWopen.niugou.service;



import com.CNFloWopen.niugou.entity.PersonInfo;

public interface PersonInfoService {

	/**
	 * 根据id获取用户信息
	 * @param userId
	 * @return
	 */
	PersonInfo getPersonInfoById(Long userId);


}
