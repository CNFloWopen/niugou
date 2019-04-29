package com.CNFloWopen.niugou;

import com.CNFloWopen.niugou.dao.ProductSellDailyDao;
import com.CNFloWopen.niugou.entity.ProductSellDaily;
import com.CNFloWopen.niugou.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyDaoTest {
    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Test
    public void testInsertProductSellDaily()
    {
//       创建的日销量统计
        int effectedNum = productSellDailyDao.insertProductSellDaily();
        assertEquals(3,effectedNum);
    }

    @Test
    public void testQueryProductSellDailyList()
    {
        ProductSellDaily productSellDaily = new ProductSellDaily();
//        叠加店铺去查询
        Shop shop = new Shop();
        shop.setShopId(1L);
        productSellDaily.setShop(shop);
        List<ProductSellDaily> productSellDailyList = productSellDailyDao.queryProductSellDailyList(productSellDaily,null,null);
        assertEquals(2,productSellDailyList.size());
    }
}
