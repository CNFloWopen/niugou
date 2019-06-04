package com.CNFloWopen.niugou;

import com.CNFloWopen.niugou.dao.ShopDao;
import com.CNFloWopen.niugou.entity.Shop;
import com.CNFloWopen.niugou.enums.ShopStateEnum;
import com.CNFloWopen.niugou.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopDaoTest {
    @Autowired
    ShopDao shopDao;
    @Autowired
    ShopService shopService;

    @Test
    public void delShop()
    {
        shopDao.deleteShopByEnable(22L);
    }

    @Test
    public void testEnum()
    {
        ShopStateEnum stateEnum = ShopStateEnum.stateOf(-1003);
        System.out.println(stateEnum.getStateInfo());
        System.out.println(ShopStateEnum.SUCCESS.getStateInfo());
    }

}
