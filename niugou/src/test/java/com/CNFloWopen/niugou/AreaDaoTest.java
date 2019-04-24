package com.CNFloWopen.niugou;


import com.CNFloWopen.niugou.dao.AreaDao;
import com.CNFloWopen.niugou.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest{
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