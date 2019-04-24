package com.CNFloWopen.niugou.dao;


import com.CNFloWopen.niugou.entity.HeadLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface HeadLineDao {
    /**
     *根据传入的查询条件（头条名查询头条）
     * @return
     */
    List<HeadLine> queryHeadLine(
            @Param("headLineCondition") HeadLine headLineCondition);



}
