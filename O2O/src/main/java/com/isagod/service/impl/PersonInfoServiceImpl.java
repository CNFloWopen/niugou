package com.isagod.service.impl;

import java.util.List;

import com.isagod.dao.PersonInfoDao;
import com.isagod.entity.PersonInfo;
import com.isagod.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PersonInfoServiceImpl implements PersonInfoService {
	@Autowired
	private PersonInfoDao personInfoDao;
	//根据id获取用户信息
	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		return personInfoDao.queryPersonInfoById(userId);
	}



}
