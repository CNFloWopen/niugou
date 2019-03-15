package com.isagod.service.impl;

import java.util.Date;

import com.isagod.dao.PersonInfoDao;
import com.isagod.dao.WechatAuthDao;
import com.isagod.dto.WechatAuthExecution;
import com.isagod.entity.PersonInfo;
import com.isagod.entity.WechatAuth;
import com.isagod.enums.WechatAuthStateEnum;
import com.isagod.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Service
public class WechatAuthServiceImpl implements WechatAuthService {
	private static Logger log = LoggerFactory
			.getLogger(WechatAuthServiceImpl.class);
	@Autowired
	private WechatAuthDao wechatAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthDao.queryWechatInfoByOpenId(openId);
	}

	@Override
	@Transactional
	public WechatAuthExecution register(WechatAuth wechatAuth) throws RuntimeException {
		if (wechatAuth == null || wechatAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}
		try {
			wechatAuth.setCreateTime(new Date());
			if (wechatAuth.getPersonInfo() != null
					&& wechatAuth.getPersonInfo().getUserId() == null) {
//				if (profileImg != null) {
//					try {
//						addProfileImg(wechatAuth, profileImg);
//					} catch (Exception e) {
//						log.debug("addUserProfileImg error:" + e.toString());
//						throw new RuntimeException("addUserProfileImg error: "
//								+ e.getMessage());
//					}
//				}
				try {
					wechatAuth.getPersonInfo().setCreateTime(new Date());


					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					int effectedNum = personInfoDao
							.insertPersonInfo(personInfo);
					wechatAuth.setPersonInfo(personInfo);
					if (effectedNum <= 0) {
						throw new RuntimeException("添加用户信息失败");
					}
				} catch (Exception e) {
					log.debug("insertPersonInfo error:" + e.toString());
					throw new RuntimeException("insertPersonInfo error: "
							+ e.getMessage());
				}
			}
			int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号创建失败");
			} else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,
						wechatAuth);
			}
		} catch (Exception e) {
			log.debug("insertWechatAuth error:" + e.toString());
			throw new RuntimeException("insertWechatAuth error: "
					+ e.getMessage());
		}
	}

//	private void addProfileImg(WechatAuth wechatAuth,
//			CommonsMultipartFile profileImg) {
//		String dest = FileUtil.getPersonInfoImagePath();
//		String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);
//		wechatAuth.getPersonInfo().setProfileImg(profileImgAddr);
//	}

}
