package com.CNFloWopen.niugou.service;


import com.CNFloWopen.niugou.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest  {
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;
    /**
     * 测试Service方法
     */
    @Test
    public void testGetAreaList()
    {

        List<Area> list = areaService.getAreaList();
        assertEquals("西苑",list.get(0).getAreaName());
        cacheService.removeFromCache(areaService.AREALISTKEY);
        list = areaService.getAreaList();
    }



}