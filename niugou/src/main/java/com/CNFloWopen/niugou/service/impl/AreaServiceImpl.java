package com.CNFloWopen.niugou.service.impl;

import com.CNFloWopen.niugou.cache.JedisUtil;
import com.CNFloWopen.niugou.dao.AreaDao;

import com.CNFloWopen.niugou.entity.Area;

import com.CNFloWopen.niugou.service.AreaService;
import com.CNFloWopen.niugou.service.CacheService;
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

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private CacheService cacheService;

    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
    @Override
    @Transactional
    public List<Area> getAreaList() {
        String key = AREALISTKEY;
        List<Area> areaList = null;
//        因为要以键值对的方式存储，数据转化操作类 。
        ObjectMapper mapper = new ObjectMapper();
//        cacheService.removeFromCache(key);
        if (!jedisKeys.exists(key))
        {
            areaList= areaDao.queryArea();
            String jsonString = null;
            try {
//                将areaList的json数组转为string形式
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            jedisStrings.set(key,jsonString);
        }else {
            String jsonString = jedisStrings.get(key);
//            获取类型创建工厂，把ArrayList.class转为Area.class
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
            try {
//                将string对象转为java对象，Area.class
                areaList = mapper.readValue(jsonString,javaType);

            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
        return areaList;
    }


}