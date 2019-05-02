package com.CNFloWopen.niugou.service;

import com.CNFloWopen.niugou.dto.UserProductMapExecution;
import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.entity.Product;
import com.CNFloWopen.niugou.entity.Shop;
import com.CNFloWopen.niugou.entity.UserProductMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProductMapServiceTest {
    @Autowired
    private UserProductMapService userProductMapService;


    @Test
    public void testBQueryUserProductMapList()
    {
        UserProductMap userProductMap = new UserProductMap();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(6L);
        userProductMap.setUser(personInfo);

        UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMap,0,2);
//        for (UserProductMap p:ue.getUserProductMapList())
//        {
//            System.out.println(p);
//        }

        Shop shop = new Shop();
        shop.setShopId(1L);
        userProductMap.setShop(shop);
        ue = userProductMapService.listUserProductMap(userProductMap,0,2);
        for (UserProductMap p:ue.getUserProductMapList())
        {
            System.out.println(p);
        }


//
//
//        userProductMap.setShop(shop);




    }
}
