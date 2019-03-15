package com.isagod.service;

import com.isagod.BaseTest;
import com.isagod.dto.ImageHolder;
import com.isagod.dto.ShopExecution;
import com.isagod.entity.Area;
import com.isagod.entity.PersonInfo;
import com.isagod.entity.Shop;
import com.isagod.entity.ShopCategory;
import com.isagod.enums.ShopStateEnum;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;
    @Test
    public void testGetShopList()
    {
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(2l);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition,2,2);
        System.out.println(se.getShopList().size());
//        System.out.println(se.getShopList());
        System.out.println(se.getCount());
    }


    @Test
//    @Ignore
    public void testModifyShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(1l);
        shop.setShopName("修改后的商铺名称");
        File file = new File("/users/argeszy/resources/test.jpg");
        InputStream is = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder("test.jpg",is);
        shopService.modifyShop(shop,imageHolder);
    }

    @Test
//    @Ignore
    public void testShopService() throws FileNotFoundException {
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
        shop.setShopName("店铺5");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setPhone("15513001");
        shop.setCreateTime(new Date());
        shop.setAdvice("审核中");
        File file = new File("/users/argeszy/resources/test.jpg");
        InputStream is = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder(file.getName(),is);
        ShopExecution se = shopService.addShop(shop,imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }
}