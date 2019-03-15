package com.isagod.dao;

import com.isagod.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    /**
     * 返回商品图片的id列表
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

    /**
     * 批量添加图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);
}
