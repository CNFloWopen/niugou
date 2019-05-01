package com.CNFloWopen.niugou.service.impl;

import java.util.Date;
import java.util.List;

import com.CNFloWopen.niugou.dao.ShopAuthMapDao;
import com.CNFloWopen.niugou.dto.ShopAuthMapExecution;
import com.CNFloWopen.niugou.entity.ShopAuthMap;
import com.CNFloWopen.niugou.enums.ShopAuthMapStateEnum;
import com.CNFloWopen.niugou.service.ShopAuthMapService;
import com.CNFloWopen.niugou.util.PageCalculatop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;

	@Override
	public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId,

														Integer pageIndex, Integer pageSize) {
//		空值判断
		if (shopId != null && pageIndex != null && pageSize != null) {
//			页转行
			int beginIndex = PageCalculatop.calculateRowIndex(pageIndex,
					pageSize);
//			查询返回该店铺的授权信息列表
			List<ShopAuthMap> shopAuthMapList = shopAuthMapDao
					.queryShopAuthMapListByShopId(shopId, beginIndex, pageSize);
//			返回总数
			int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
			ShopAuthMapExecution se = new ShopAuthMapExecution();
			se.setShopAuthMapList(shopAuthMapList);
			se.setCount(count);
			return se;
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap)
			throws RuntimeException {
//		空值判断，主要是对店铺id和员工id做效验
		if (shopAuthMap != null && shopAuthMap.getShop() != null
				&& shopAuthMap.getEmployee() != null && shopAuthMap.getEmployee().getUserId()!=null) {
			shopAuthMap.setCreateTime(new Date());
			shopAuthMap.setLastEditTime(new Date());
			shopAuthMap.setEnableStatus(1);
			try {
//				添加授权信息
				int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
				if (effectedNum <= 0) {
					throw new RuntimeException("添加授权失败");
				}
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,
						shopAuthMap);
			} catch (Exception e) {
				throw new RuntimeException("添加授权失败:" + e.toString());
			}
		} else {
			return new ShopAuthMapExecution(
					ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
		}
	}

	@Override
	@Transactional
	public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap)
			throws RuntimeException {
//		空值判断
		if (shopAuthMap == null || shopAuthMap.getShopAuthId() == null) {
			return new ShopAuthMapExecution(
					ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
		} else {
			try {
				int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
				if (effectedNum <= 0) {
					return new ShopAuthMapExecution(
							ShopAuthMapStateEnum.INNER_ERROR);
				} else {// 创建成功
					return new ShopAuthMapExecution(
							ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
				}
			} catch (Exception e) {
				throw new RuntimeException("updateShopByOwner error: "
						+ e.getMessage());
			}
		}
	}

//	@Override
//	public ShopAuthMapExecution removeShopAuthMap(Long shopAuthMapId)
//			throws RuntimeException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
		return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
	}

}
