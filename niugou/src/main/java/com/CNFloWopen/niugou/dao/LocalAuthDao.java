package com.CNFloWopen.niugou.dao;

import com.CNFloWopen.niugou.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


public interface LocalAuthDao {

	/**
	 * 通过账号密码查询对应的信息，登录用
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String userName,
										 @Param("password") String password);

	/**
	 * 通过userid查询相应用户
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalByUserId(@Param("userId") long userId);

	/**
	 * 添加平台账号
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);

	/**通过userId，userName，password更改密码
	 * 
	 * @param
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId,
                        @Param("userName") String userName,
                        @Param("password") String password,
                        @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);

	/**
	 * 检查用户名是否重复
	 * @param userName
	 * @return
	 */
	int checkLocalUserName(@Param("userName") String userName);
}
