package com.CNFloWopen.niugou.service;



import com.CNFloWopen.niugou.dto.ProductCategoryExecution;
import com.CNFloWopen.niugou.entity.ProductCategory;
import com.CNFloWopen.niugou.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 查询指定商铺下的的所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);


    /**
     * 批量添加商铺
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

       /**
     *  现将商品类别的id置空在进行，删除商品
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProdcutCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException;
}
