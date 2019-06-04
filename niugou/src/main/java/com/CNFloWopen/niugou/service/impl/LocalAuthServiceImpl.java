package com.CNFloWopen.niugou.service.impl;


import com.CNFloWopen.niugou.dao.LocalAuthDao;
import com.CNFloWopen.niugou.dao.PersonInfoDao;
import com.CNFloWopen.niugou.dto.ImageHolder;
import com.CNFloWopen.niugou.dto.LocalAuthExecution;
import com.CNFloWopen.niugou.entity.LocalAuth;
import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.enums.LocalAuthStateEnum;
import com.CNFloWopen.niugou.service.LocalAuthService;
import com.CNFloWopen.niugou.util.ImageUtil;
import com.CNFloWopen.niugou.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


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




	private void addProfileImg(LocalAuth localAuth,
							   ImageHolder profileImg) {
		String dest = PathUtil.getShopImagePath(localAuth.getLocalAuthId());
		String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);
		localAuth.getPersonInfo().setProfileImg(profileImgAddr);
	}
}
