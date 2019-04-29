package com.CNFloWopen.niugou;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import com.CNFloWopen.niugou.dao.UserProductMapDao;
import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.entity.Product;
import com.CNFloWopen.niugou.entity.Shop;
import com.CNFloWopen.niugou.entity.UserProductMap;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProductMapDaoTest {
	@Autowired
	private UserProductMapDao userProductMapDao;

	@Test
	public void testAInsertUserProductMap() throws Exception {
//		UserProductMap userProductMap = new UserProductMap();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
//		userProductMap.setUser(personInfo);
//		userProductMap.setOperator(personInfo);
//		Product product = new Product();
//		product.setProductId(2L);
//		userProductMap.setProduct(product);
//		Shop shop = new Shop();
//		shop.setShopId(1L);
//		userProductMap.setShop(shop);
//		userProductMap.setCreateTime(new Date());
//		int effectedNum = userProductMapDao
//				.insertUserProductMap(userProductMap);
		UserProductMap userProductMap1 = new UserProductMap();
		userProductMap1.setUser(personInfo);
		Product product1 = new Product();
		product1.setProductId(9L);
		userProductMap1.setProduct(product1);
		userProductMap1.setOperator(personInfo);
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		userProductMap1.setShop(shop1);
		userProductMap1.setCreateTime(new Date());
		int effectedNum = userProductMapDao
				.insertUserProductMap(userProductMap1);
		UserProductMap userProductMap2 = new UserProductMap();
		userProductMap2.setUser(personInfo);
		Product product2 = new Product();
		product2.setProductId(10L);
		userProductMap2.setProduct(product2);
		userProductMap2.setOperator(personInfo);
		Shop shop2 = new Shop();
		shop2.setShopId(1L);
		userProductMap2.setShop(shop2);
		userProductMap2.setCreateTime(new Date());
		effectedNum = userProductMapDao
				.insertUserProductMap(userProductMap2);
//		UserProductMap userProductMap1 = new UserProductMap();
//		userProductMap1.setUser(personInfo);
//		Product product1 = new Product();
//		product1.setProductId(3L);
//		userProductMap1.setProduct(product1);
//		userProductMap1.setOperator(personInfo);
//		Shop shop1 = new Shop();
//		shop1.setShopId(10L);
//		userProductMap1.setShop(shop1);
//		userProductMap1.setCreateTime(new Date());
//		effectedNum = userProductMapDao
//				.insertUserProductMap(userProductMap1);
		assertEquals(1,effectedNum);
	}

	@Test
	public void testBQueryUserProductMapList() throws Exception {
		UserProductMap userProductMap = new UserProductMap();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName("CNFloWopen");
		userProductMap.setUser(personInfo);
		List<UserProductMap> userProductMapList = userProductMapDao
				.queryUserProductMapList(userProductMap, 0, 2);
		assertEquals(2, userProductMapList.size());
		int count = userProductMapDao.queryUserProductMapCount(userProductMap);
		assertEquals(2, count);

		Shop shop = new Shop();
		shop.setShopId(10L);
		userProductMap.setShop(shop);
		userProductMapList = userProductMapDao
				.queryUserProductMapList(userProductMap, 0, 1);
		assertEquals(1, userProductMapList.size());
		count = userProductMapDao.queryUserProductMapCount(userProductMap);
		assertEquals(1, count);
	}
}
