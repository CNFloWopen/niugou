package com.isagod.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isagod.cache.JedisUtil;
import com.isagod.dao.ShopCategoryDao;
import com.isagod.entity.ShopCategory;
import com.isagod.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);
    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        String key = SCLISTKEY;
        List<ShopCategory> shopCategoryList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (shopCategoryCondition == null)
        {
            key = key+"_allfirstlevel";
        }else if (shopCategoryCondition!=null && shopCategoryCondition.getParent()!=null
        && shopCategoryCondition.getParent().getShopCategoryId()!=null){
            key = key+"_parent"+shopCategoryCondition.getParent().getShopCategoryId();
        }else if (shopCategoryCondition != null)
        {
            key = key+"_allsecondlevel";
        }
        if (!jedisKeys.exists(key))
        {
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            String jsonString =null;
            try {
                jsonString = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            jedisStrings.set(key,jsonString);
        }else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,ShopCategory.class);
            try {
                shopCategoryList = mapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}