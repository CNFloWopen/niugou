package com.CNFloWopen.niugou.service.impl;

import java.util.Date;
import java.util.List;

import com.CNFloWopen.niugou.dao.UserProductMapDao;
import com.CNFloWopen.niugou.dto.UserProductMapExecution;
import com.CNFloWopen.niugou.entity.UserProductMap;
import com.CNFloWopen.niugou.enums.UserProductMapStateEnum;
import com.CNFloWopen.niugou.service.UserProductMapService;
import com.CNFloWopen.niugou.util.PageCalculatop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProductMapServiceImpl implements UserProductMapService {
	@Autowired
	private UserProductMapDao userProductMapDao;

	/**
	 * 列出用户消费列表
	 * @param userProductCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public UserProductMapExecution listUserProductMap(
			UserProductMap userProductCondition, Integer pageIndex,
			Integer pageSize) {
//		空值判断
		if (userProductCondition != null && pageIndex != null
				&& pageSize != null) {
//			页转行
			int beginIndex = PageCalculatop.calculateRowIndex(pageIndex,
					pageSize);
//			依据查询条件分页取出列表
			List<UserProductMap> userProductMapList = userProductMapDao
					.queryUserProductMapList(userProductCondition, beginIndex,
							pageSize);
//			依据同等的查询条件获取总数
			int count = userProductMapDao
					.queryUserProductMapCount(userProductCondition);
			UserProductMapExecution se = new UserProductMapExecution();
			se.setUserProductMapList(userProductMapList);
			se.setCount(count);
			return se;
		} else {
			return null;
		}

	}

	/**
	 * 添加消费记录
	 * @param userProductMap
	 * @return
	 * @throws RuntimeException
	 */
	@Override
	@Transactional
	public UserProductMapExecution addUserProductMap(
			UserProductMap userProductMap) throws RuntimeException {
//		空值判断，主要确保顾客id，店铺id以及操作员id为非空
		if (userProductMap != null && userProductMap.getUser().getUserId() != null
				&& userProductMap.getShop().getShopId()!= null &&
				userProductMap.getOperator().getUserId()!=null) {
//			设置默认值，消费记录的创建时间
			userProductMap.setCreateTime(new Date());
			try {
//				添加消费记录
				int effectedNum = userProductMapDao
						.insertUserProductMap(userProductMap);
				if (effectedNum <= 0) {
					throw new RuntimeException("添加消费记录失败");
				}

				return new UserProductMapExecution(
						UserProductMapStateEnum.SUCCESS, userProductMap);
			} catch (Exception e) {
				throw new RuntimeException("添加授权失败:" + e.toString());
			}
		} else {
			return new UserProductMapExecution(
					UserProductMapStateEnum.NULL_USERPRODUCT_INFO);
		}
	}



}
