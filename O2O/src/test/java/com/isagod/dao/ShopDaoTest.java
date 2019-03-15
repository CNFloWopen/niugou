package com.isagod.dao;

import com.isagod.BaseTest;
import com.isagod.entity.Area;
import com.isagod.entity.PersonInfo;
import com.isagod.entity.Shop;
import com.isagod.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;
    //测试版
    @Test
    public void testQueryShopListAndCount()
    {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1l);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println(count);
        System.out.println("店铺列表大小"+shopList.size());
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(2l);
        shopCondition.setShopCategory(sc);
        shopList = shopDao.queryShopList(shopCondition, 0, 2);
        count = shopDao.queryShopCount(shopCondition);
        System.out.println("新的："+count);
        System.out.println("店铺列表大小："+shopList.size());

    }
    //改进版
    @Test
    public void testQueryShopListAndCount2()
    {
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(4L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 6);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println(count);
        System.out.println("店铺列表大小"+shopList.size());


    }
    @Test
//    @Ignore
    public void testQueryByShopId()
    {
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId:--"+shop.getArea().getAreaName());
        System.out.println("shopcategory:--"+shop.getShopCategory().getShopCategoryName());
    }


    @Test
    @Ignore
    public void testInsertShop()
    {
        Shop shop = new Shop();
        Area area = new Area();
        PersonInfo personInfo = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();
        area.setAreaId(1);
        personInfo.setUserId(1L);
        shopCategory.setShopCategoryId(1L);
        shop.setArea(area);
        shop.setOwner(personInfo);
        shop.setShopCategory(shopCategory);
        shop.setShopName("test");
        shop.setEnableStatus(1);
        shop.setPhone("test");
        shop.setCreateTime(new Date());
        shop.setAdvice("好好开");

        int i = shopDao.insertShop(shop);
        assertEquals(1,i);
    }

    @Test
    @Ignore
    public void testUpdateShop()
    {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setAdvice("审核中");
        int i = shopDao.updateShop(shop);
        assertEquals(1,i);
    }
}