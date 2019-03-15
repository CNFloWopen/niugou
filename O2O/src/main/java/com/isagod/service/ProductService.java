package com.isagod.service;

import com.isagod.dto.ImageHolder;
import com.isagod.dto.ProductExecution;
import com.isagod.entity.Product;
import com.isagod.exceptions.ProductCategoryOperationException;
import com.isagod.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

public interface ProductService {
    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException;

    /**
     * 查找商品列表并分页，可输入的条件：商品名（模糊），商品状态，店铺ID，商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 查找商品ID
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 修改商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgs
     * @return
     * @throws RuntimeException
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgs) throws ProductOperationException;
}
