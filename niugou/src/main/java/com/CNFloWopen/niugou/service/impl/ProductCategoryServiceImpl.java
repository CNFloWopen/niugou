package com.CNFloWopen.niugou.service.impl;


import com.CNFloWopen.niugou.dao.ProductCategoryDao;
import com.CNFloWopen.niugou.dao.ProductDao;
import com.CNFloWopen.niugou.dto.ProductCategoryExecution;
import com.CNFloWopen.niugou.entity.ProductCategory;
import com.CNFloWopen.niugou.enums.ProductCategoryStateEnum;
import com.CNFloWopen.niugou.exceptions.ProductCategoryOperationException;
import com.CNFloWopen.niugou.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    //进行业务层的商品分类
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }
    //批量添加商铺
    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if(productCategoryList != null && productCategoryList.size() > 0)
        {
            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum<=0)
                {
                    throw new ProductCategoryOperationException("创建店铺失败");
                }else {
                    //创建成功
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (ProductCategoryOperationException e) {
                throw new ProductCategoryOperationException("batchProductCategoryList error"+e.getMessage());
            }
            //创建失败
        }else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }
//    删除商品
    @Override
    @Transactional //开启事务
    public ProductCategoryExecution deleteProdcutCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //删除前先将商品类别id置空
        //解除tb_product里的商品与该ProductCategoryId的关联
        try {
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectedNum < 0)
            {
                throw new ProductCategoryOperationException("更新商品类别信息失败");
            }
        }catch (Exception e)
        {
            throw  new ProductCategoryOperationException("deleteProdcutCategory error"+e.getMessage());
        }
        //删除该ProductCategory
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if (effectedNum<=0)
            {
                throw new ProductCategoryOperationException("删除商品类别失败");
            }else
            {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e)
        {
            throw new ProductCategoryOperationException("deleteProductCategory error"+e.getMessage());
        }
    }

}
