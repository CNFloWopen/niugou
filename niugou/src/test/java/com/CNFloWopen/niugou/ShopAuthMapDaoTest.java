package com.CNFloWopen.niugou;

import com.CNFloWopen.niugou.dao.ShopAuthMapDao;
import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.entity.Shop;
import com.CNFloWopen.niugou.entity.ShopAuthMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopAuthMapDaoTest {
    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Test
    public void testInsertShopAuthMap()
    {
//        创建店铺授权信息
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        PersonInfo employee   = new PersonInfo();
        employee.setUserId(1L);
        shopAuthMap.setEmployee(employee);
        Shop shop = new Shop();
        shop.setShopId(1L);
        shopAuthMap.setShop(shop);
        shopAuthMap.setTitle("CEO");
        shopAuthMap.setTitleFlag(1);
        shopAuthMap.setCreateTime(new Date());
        shopAuthMap.setLastEditTime(new Date());
        shopAuthMap.setEnableStatus(1);
        int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
        assertEquals(1,effectedNum);
        ShopAuthMap shopAuthMap1 = new ShopAuthMap();
        shopAuthMap1.setEmployee(employee);
        Shop shop1 = new Shop();
        shop1.setShopId(10L);
        shopAuthMap1.setShop(shop1);
        shopAuthMap1.setTitle("CEO");
        shopAuthMap1.setTitleFlag(1);
        shopAuthMap1.setCreateTime(new Date());
        shopAuthMap1.setLastEditTime(new Date());
        shopAuthMap1.setEnableStatus(1);
        effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap1);
        assertEquals(1,effectedNum);

    }

    /**
     * 测试查询功能
     */
    @Test
    public void testQueryShopAuthMap()
    {
//            测试queryShopAuthMapListShopId
        List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(1,0,2);
        assertEquals(1,shopAuthMapList.size());
//        测试queryShopAuthMapById
        ShopAuthMap shopAuthMap = shopAuthMapDao.queryShopAuthMapById(shopAuthMapList.get(0).getShopAuthId());
        assertEquals("CEO",shopAuthMap.getTitle());
        System.out.println("employee name is:"+shopAuthMap.getEmployee().getName());
        System.out.println("shop name is:"+shopAuthMap.getShop().getShopName());
//        测试queryShopAuthCountByShopId
        int count = shopAuthMapDao.queryShopAuthCountByShopId(1);
        assertEquals(1,count);
    }

//    测试更新功能
    @Test
    public void testUpdateShopAuthMap()
    {
        List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(1,0,1);
        shopAuthMapList.get(0).setTitle("CCO");
        shopAuthMapList.get(0).setTitleFlag(2);
        int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMapList.get(0));
        assertEquals(1,effectedNum);
    }
//    测试删除功能
    @Test
    public void testDeleteShopAuthMap()
    {
        List<ShopAuthMap> shopAuthMapList1 = shopAuthMapDao.queryShopAuthMapListByShopId(1,0,1);
        List<ShopAuthMap> shopAuthMapList2 = shopAuthMapDao.queryShopAuthMapListByShopId(10,0,1);
        int effectedNum = shopAuthMapDao.deleteShopAuthMap(shopAuthMapList1.get(0).getShopAuthId());
        assertEquals(1,effectedNum);
        effectedNum = shopAuthMapDao.deleteShopAuthMap(shopAuthMapList2.get(0).getShopAuthId());
        assertEquals(1,effectedNum );
    }

}
