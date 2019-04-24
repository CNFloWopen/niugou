package com.CNFloWopen.niugou.service;



import com.CNFloWopen.niugou.dto.ImageHolder;
import com.CNFloWopen.niugou.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
    public static final String HLLISTKEY = "headlinelist";
    /**
     *根据传入的条件返回指定的头条列表
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition)
            throws IOException;


}
