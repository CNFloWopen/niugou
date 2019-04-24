package com.CNFloWopen.niugou.util;

/**
 *由于前端只认页数，而后端的dao却只认函数。所以必须做一个转化
 */
public class PageCalculatop {
    //获取的是页数，由于rowIndex获取的是从哪条开始
    public static int calculateRowIndex(int pageIndex,int pageSzie){
        return (pageIndex > 0)?(pageIndex-1)*pageSzie:0;
    }
}
