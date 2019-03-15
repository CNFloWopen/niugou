package com.isagod.dao;

import com.isagod.BaseTest;
import com.isagod.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.junit.Assert.*;
public class HeadlineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;
    @Test
    public void queryAre()
    {
       List<HeadLine> list = headLineDao.queryHeadLine(new HeadLine());
       assertEquals(4,list.size());
    }
}
