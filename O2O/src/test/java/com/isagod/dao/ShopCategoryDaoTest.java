package com.isagod.dao;

        import com.isagod.BaseTest;
        import com.isagod.entity.ShopCategory;
        import org.junit.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import java.util.List;

        import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void testQueryShopCategory(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
//        assertEquals(2,shopCategoryList.size());
        ShopCategory testCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        testCategory.setParent(parentCategory);
        shopCategoryList = shopCategoryDao.queryShopCategory(testCategory);
        assertEquals(1,shopCategoryList.size());
        System.out.println(shopCategoryList.get(0).getShopCategoryName());
    }

    /**
     * 查看parent_id为空的条数
     */
    @Test
    public void testQueryShopCategory2(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
        System.out.println(shopCategoryList.size());
    }
}