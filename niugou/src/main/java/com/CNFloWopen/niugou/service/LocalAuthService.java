package com.CNFloWopen.niugou.service;


import com.CNFloWopen.niugou.dto.ImageHolder;
import com.CNFloWopen.niugou.dto.LocalAuthExecution;
import com.CNFloWopen.niugou.entity.LocalAuth;

public interface LocalAuthService {
	/**
	 * 通过账号和密码获取平台账号信息
	 * @param userName
	 * @return
	 */
	LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

	/**
	 * 通过userId获取平台账号信息
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);

	/**
	 *
	 * @param localAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution register(LocalAuth localAuth,
                                ImageHolder profileImg) throws RuntimeException;

	/**
	 * 绑定微信，生成平台专属账号
	 * @param localAuth
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws RuntimeException;

	/**
	 * 修改平台的账号密码
	 * @param userId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param
	 * @return
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                       String password, String newPassword);
}
