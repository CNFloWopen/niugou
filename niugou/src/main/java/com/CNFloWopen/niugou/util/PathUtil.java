package com.CNFloWopen.niugou.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathUtil {
    //获取分隔符
    private static String separator = System.getProperty("file.separator");
    private static String winPath;


    private static String linuxPath;


    private static String shopPath;

    @Value("${win.base.path}")
    public  void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }
    @Value("${linux.base.path}")
    public  void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }
    @Value("${shop.relevant.path}")
    public  void setShopPath(String shopPath) {
        PathUtil.shopPath = shopPath;
    }






    //根据不同的系统进行存储根目录
    public static String getImgBasePath()
    {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win"))
        {
            basePath=winPath;
        }else {
            basePath=linuxPath;
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }
    //用户上传
    public static String getShopImagePath(Long shopId)
    {
        String imagePath = shopPath+shopId+separator;
        return imagePath.replace("/",separator);
    }
}