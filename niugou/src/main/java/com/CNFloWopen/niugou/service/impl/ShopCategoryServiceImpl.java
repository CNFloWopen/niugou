package com.CNFloWopen.niugou.service.impl;

import com.CNFloWopen.niugou.cache.JedisUtil;
import com.CNFloWopen.niugou.dao.ShopCategoryDao;
import com.CNFloWopen.niugou.dto.ImageHolder;

import com.CNFloWopen.niugou.entity.ShopCategory;

import com.CNFloWopen.niugou.service.ShopCategoryService;
import com.CNFloWopen.niugou.util.ImageUtil;
import com.CNFloWopen.niugou.util.PathUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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