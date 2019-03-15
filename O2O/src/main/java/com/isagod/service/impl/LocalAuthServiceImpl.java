package com.isagod.service.impl;

import java.util.Date;

import com.isagod.dao.LocalAuthDao;
import com.isagod.dao.PersonInfoDao;
import com.isagod.dto.LocalAuthExecution;
import com.isagod.entity.LocalAuth;
import com.isagod.enums.LocalAuthStateEnum;
import com.isagod.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Service
public class LocalAuthServiceImpl implements LocalAuthService {

	@Autowired
	private LocalAuthDao localAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;
	//账号密码查询信息
	@Override
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName,
												  String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(userName, password);
	}
	//根据id查询信息
	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}

	//绑定微信账号
	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws RuntimeException {
		if (localAuth == null || localAuth.getPassword() == null
				|| localAuth.getUserName() == null || localAuth.getPersonInfo() == null
				|| localAuth.getPersonInfo().getUserId() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
		if (tempAuth != null) {
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(localAuth.getPassword());
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号绑定失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
						localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: "
					+ e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName,
			String password, String newPassword) {
		if (userId != null && userName != null && password != null
				&& newPassword != null && !password.equals(newPassword)) {
			try {
				int effectedNum = localAuthDao.updateLocalAuth(userId,
						userName, password,
						newPassword, new Date());
				if (effectedNum <= 0) {
					throw new RuntimeException("更新密码失败");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new RuntimeException("更新密码失败:" + e.toString());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}


}
