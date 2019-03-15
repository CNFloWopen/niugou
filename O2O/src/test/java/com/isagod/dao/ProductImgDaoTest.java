package com.isagod.dao;

import com.isagod.BaseTest;
import com.isagod.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
//测试全部成功
//形成添加、查询、删除的测试回环。这是最好用的测试方法之一
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;
//    @Ignore
    @Test
    public void testBQueryProductImgList() {
        //检查productId为1的商品里是否有2个详情图片记录
        List<ProductImg> productImgList = productImgDao.queryProductImgList(2L);
        assertEquals(2,productImgList.size());

    }

    @Test
    public void testABatchInsertProductImg() {
        //productId为1的商品里添加2个详情图片记录
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片test1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(2L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片test2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(2L);
        List<ProductImg> list = new ArrayList<ProductImg>();
        list.add(productImg1);
        list.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(list);
        assertEquals(2,effectedNum);

    }
//    @Ignore
    @Test
    public void testCDeleteProductImgByProductId() {
        //删除productId为1的商品里2个详情图片记录
        long productId = 2;
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effectedNum);
    }
}