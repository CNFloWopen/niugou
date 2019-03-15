package com.isagod.service;

import com.isagod.dto.ImageHolder;
import com.isagod.dto.ShopExecution;
import com.isagod.entity.Shop;

import java.io.File;
import java.io.InputStream;

public interface ShopService {
    /**
     *  添加店铺,并对托片进行处理
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution addShop(Shop shop , ImageHolder thumbnail);

    /**
     * 根据商品id获取商品信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息。并对图片进行处理
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail);

    /**
     * 根据shopCondition条件返回相应数据
     * @param shopCondition
     * 为什么用pageIndex，而不是rowIndex====由于前端只认页数，而后端的dao却只认函数。所以必须做一个转化
     * 获取的是页数，由于rowIndex获取的是从哪条开始
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}