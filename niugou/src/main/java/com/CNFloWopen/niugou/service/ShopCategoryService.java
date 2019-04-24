package com.CNFloWopen.niugou.service;


import com.CNFloWopen.niugou.dto.ImageHolder;

import com.CNFloWopen.niugou.entity.ShopCategory;

import java.io.IOException;
import java.util.List;

public interface ShopCategoryService {
    public static final String SCLISTKEY = "shopcategorylist";
    /**
     * 根据查询条件获取shopCategory列表
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}