package com.CNFloWopen.niugou.dao;


import com.CNFloWopen.niugou.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PersonInfoDao {

//	/**
//	 *
//	 * @param personInfoCondition
//	 * @param rowIndex
//	 * @param pageSize
//	 * @return
//	 */
//	List<PersonInfo> queryPersonInfoList(
//            @Param("personInfoCondition") PersonInfo personInfoCondition,
//            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
//
//	/**
//	 *
//	 * @param personInfoCondition
//	 * @return
//	 */
//	int queryPersonInfoCount(
//            @Param("personInfoCondition") PersonInfo personInfoCondition);

	/**
	 * 通过用户id查询用户
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);

	/**
	 * 添加用户信息
	 * @param
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);


//
//	/**
//	 *
//	 * @param
//	 * @return
//	 */
//	int deletePersonInfo(long userId);
}
