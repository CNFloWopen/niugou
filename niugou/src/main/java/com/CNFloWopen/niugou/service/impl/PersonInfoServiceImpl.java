package com.CNFloWopen.niugou.service.impl;


import com.CNFloWopen.niugou.dao.PersonInfoDao;

import com.CNFloWopen.niugou.entity.PersonInfo;

import com.CNFloWopen.niugou.service.PersonInfoService;
import com.CNFloWopen.niugou.util.PageCalculatop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
