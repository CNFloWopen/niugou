package com.isagod.dao;

import com.isagod.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;
public interface ProductCategoryDao {
    /**
     * 通过shopId获取查询商铺类别
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除商品功能
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}
