package com.isagod.dao;

import com.isagod.BaseTest;
import com.isagod.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import java.util.List;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    /**
     * 测试Dao的方法
     */
    @Test
    public void testQueryArea()
    {
        List<Area> list = areaDao.queryArea();
        System.out.println(list);
    }
}