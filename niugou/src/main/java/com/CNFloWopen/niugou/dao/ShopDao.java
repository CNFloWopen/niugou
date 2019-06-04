package com.CNFloWopen.niugou.dao;


import com.CNFloWopen.niugou.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ShopDao {
    /**
     * 添加店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 通过shop id 查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域Id，用户信息
     * @param有多个的话用，代表的是唯一标识
     * @param shopCondition 查询的条件
     * @param rowIndex 从第几行开始去数据
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * queryShopList返回查询到的结果总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * 删除店铺
     *
     * @param shopId
     * @return effectedNum
     */
    void deleteShopByEnable(long shopId);

}