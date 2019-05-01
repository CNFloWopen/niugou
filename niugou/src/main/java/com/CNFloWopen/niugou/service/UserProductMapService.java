package com.CNFloWopen.niugou.service;


import com.CNFloWopen.niugou.dto.UserProductMapExecution;
import com.CNFloWopen.niugou.entity.UserProductMap;

public interface UserProductMapService {
	/**
	 * 通过传入的查询条件分页列出用户消费记录列表
	 * @param userProductCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserProductMapExecution listUserProductMap(
			UserProductMap userProductCondition, Integer pageIndex,
			Integer pageSize);

//	/**
//	 *
//	 * @param userProductMap
//	 * @return
//	 * @throws RuntimeException
//	 */
//	UserProductMapExecution addUserProductMap(UserProductMap userProductMap)
//			throws RuntimeException;

}
