package com.isagod.service;

import com.isagod.BaseTest;
import com.isagod.dto.ImageHolder;
import com.isagod.dto.ProductExecution;
import com.isagod.entity.Product;
import com.isagod.entity.ProductCategory;
import com.isagod.entity.Shop;
import com.isagod.enums.ProductCategoryStateEnum;
import com.isagod.enums.ProductStateEnum;
import com.isagod.exceptions.ProductOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class testProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Test
    public void addProduct() throws FileNotFoundException, ProductOperationException {
        //创建shopId为且productCategoryId为1的商品实例并给其他成员赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1的描述");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //创建缩略图文件流
        File thumbnailFile = new File("/users/argeszy/resources/test.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);
        //创建2个商品详情图片并将它们添加到商品详情列表中
        File productImg1 = new File("/users/argeszy/resources/test2.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("/users/argeszy/resources/test2.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));
        //添加商品并验证
        ProductExecution pe = productService.addProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }

    @Test
    public void ModifyProduct() throws FileNotFoundException, ProductOperationException {
        //创建shopId为且productCategoryId为1的商品实例并给其他成员赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setProductId(3L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("更改wan后");
        product.setProductDesc("更改后的描述");
        //创建缩略图文件流
        File thumbnailFile = new File("/users/argeszy/resources/test.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);
        //创建2个商品详情图片并将它们添加到商品详情列表中
        File productImg1 = new File("/users/argeszy/resources/test2.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("/users/argeszy/resources/test2.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));
        //添加商品并验证
        ProductExecution pe = productService.modifyProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }

    @Test
    public void testBGetByShopId() throws Exception {
        long productId = 2L;
        Product product = productService.getProductById(productId);
//        List<ProductCategory> productCategoryList = productCategoryService
//                .getProductCategoryList(product.getShop().getShopId());
//        System.out.println(productCategoryList.size());

        System.out.println(product.toString());
    }
}