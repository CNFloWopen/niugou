package com.CNFloWopen.niugou.service;


import com.CNFloWopen.niugou.dto.ShopAuthMapExecution;
import com.CNFloWopen.niugou.entity.ShopAuthMap;

public interface ShopAuthMapService {
	/**
	 * 根据店铺id分页显示该店铺的授权信息
	 * @param shopId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopAuthMapExecution listShopAuthMapByShopId(Long shopId,
												 Integer pageIndex, Integer pageSize);

	/**
	 * 添加授权信息
	 * @param shopAuthMap
	 * @return
	 * @throws RuntimeException
	 */
	ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap)
			throws RuntimeException;

	/**
	 * 更新授权信息，包括职位等
	 * @param shopAuthMap
	 * @return
	 * @throws RuntimeException
	 */
	ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws RuntimeException;

//	/**
//	 *
//	 * @param shopAuthMapId
//	 * @return
//	 * @throws RuntimeException
//	 */
//	ShopAuthMapExecution removeShopAuthMap(Long shopAuthMapId)
//			throws RuntimeException;

	/**
	 * 根据shopAuthId返回对应的授权信息
	 * @param shopAuthId
	 * @return
	 */
	ShopAuthMap getShopAuthMapById(Long shopAuthId);

}
