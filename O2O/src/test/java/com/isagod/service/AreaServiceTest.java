package com.isagod.service;

import com.isagod.BaseTest;
import com.isagod.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;


import java.util.List;

public class AreaServiceTest extends BaseTest {
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