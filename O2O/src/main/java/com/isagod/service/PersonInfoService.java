package com.isagod.service;


import com.isagod.entity.PersonInfo;

public interface PersonInfoService {

	/**
	 * 根据id获取用户信息
	 * @param userId
	 * @return
	 */
	PersonInfo getPersonInfoById(Long userId);

//	/**
//	 *
//	 * @param personInfoCondition
//	 * @param pageIndex
//	 * @param pageSize
//	 * @return
//	 */
//	PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition,
//                                          int pageIndex, int pageSize);
//
//	/**
//	 *
//	 * @param personInfo
//	 * @return
//	 */
//	PersonInfoExecution addPersonInfo(PersonInfo personInfo);
//
//	/**
//	 *
//	 * @param personInfo
//	 * @return
//	 */
//	PersonInfoExecution modifyPersonInfo(PersonInfo personInfo);

}
