package com.isagod.util;

import java.io.File;

public class PathUtil {
    //获取分隔符
    private static String separator = System.getProperty("file.separator");

    //根据不同的系统进行存储根目录
    public static String getImgBasePath()
    {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win"))
        {
            basePath="D:/projectO2O/image";
        }else {
            basePath="/Users/argeszy/resources/image";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }
    //用户上传
    public static String getShopImagePath(Long shopId)
    {
        String imagePath = "/upload/item/shop"+"/"+shopId+"/";
        return imagePath.replace("/",separator);
    }
}