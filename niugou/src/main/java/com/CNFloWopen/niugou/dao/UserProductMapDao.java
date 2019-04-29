package com.CNFloWopen.niugou.dao;

import java.util.List;

import com.CNFloWopen.niugou.entity.UserProductMap;
import org.apache.ibatis.annotations.Param;


public interface UserProductMapDao {

	/**
	 * 根据查询条件返回用户购买商品的记录列表
	 * @param userProductCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserProductMap> queryUserProductMapList(
            @Param("userProductCondition") UserProductMap userProductCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 配合queryUserProductMapList根据相同条件返回用户购买商品的记录总数
	 * @param userProductCondition
	 * @return
	 */
	int queryUserProductMapCount(
            @Param("userProductCondition") UserProductMap userProductCondition);

	/**添加一条购买商品的记录
	 * 
	 * @param userProductMap
	 * @return
	 */
	int insertUserProductMap(UserProductMap userProductMap);
}
