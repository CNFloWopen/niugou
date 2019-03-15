package com.isagod.dao;

import com.isagod.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    /**
     * 获取商铺类别
     * 由于分类有多个参数所以要用到====-----@Param
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}