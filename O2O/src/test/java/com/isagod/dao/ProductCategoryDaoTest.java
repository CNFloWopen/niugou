package com.isagod.dao;

import com.isagod.BaseTest;
import com.isagod.entity.Product;
import com.isagod.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void testCQueryByShopId()
    {
        long shopId = 1;
        List<ProductCategory> productcategoryList =productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("自定义的类别商铺个数为："+productcategoryList.size());
    }
    @Test
    public void testABatchInsertProductCategory()
    {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商铺类别3");
        productCategory.setCreateTime(new Date());
        productCategory.setPriority(1);
        productCategory.setShopId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商铺类别4");
        productCategory2.setShopId(1L);
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2,effectedNum);

    }
//    删除的功能测试
    @Test
    public void testBDeleteProductCategory() throws Exception
    {
        long shopId = 1;
        List<ProductCategory> list = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory pc:list)
        {
            if ("商铺类别3".equals(pc.getProductCategoryName()) || "商铺类别4".equals(pc.getProductCategoryName()))
            {
                int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(),shopId);
                assertEquals(1,effectedNum);
            }
        }

    }
}
