package com.CNFloWopen.niugou.service;

import com.CNFloWopen.niugou.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Test
    public void testProductById()
    {
        Product product = new Product();
        product = productService.getProductById(2L);
    }
}
