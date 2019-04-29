package com.CNFloWopen.niugou.dao;

import com.CNFloWopen.niugou.entity.ProductSellDaily;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProductSellDailyDao {
    /**
     * 根据查询条件返回商品日销售统计列表
     * @param productSellDailyCondition
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ProductSellDaily> queryProductSellDailyList(@Param("productSellDailyCondition")
                                                     ProductSellDaily productSellDailyCondition,
                                                     @Param("beginTime")Date beginTime,@Param("endTime") Date endTime);

    /**
     * 统计平台所有商品的日销售量
     * @return
     */
    int insertProductSellDaily();
}
