package com.CNFloWopen.niugou;

import com.CNFloWopen.niugou.dao.ProductDao;
import com.CNFloWopen.niugou.entity.Product;
import com.CNFloWopen.niugou.entity.ProductImg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoTest {
    @Autowired
    private ProductDao productDao;
    @Test
    public void testGetProductById()
    {
        Product product = productDao.queryProductById(11);
        for (ProductImg p :product.getProductImgList()
                ) {
            System.out.println(p.getImgAddr());
        }
    }
}
