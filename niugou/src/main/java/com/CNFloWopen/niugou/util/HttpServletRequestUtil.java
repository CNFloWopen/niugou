package com.CNFloWopen.niugou.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 接收request的信息，并转化类型
 */
public class HttpServletRequestUtil {
    //    把字符串类型的键==转化整型的数据
    public static int getInt(HttpServletRequest request,String key)
    {
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }
    //    把字符串类型的键==转化long的数据
    public static Long getLong(HttpServletRequest request,String key)
    {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1L;
        }
    }
    //    把字符串类型的键==转化double的数据
    public static Double getDouble(HttpServletRequest request,String key)
    {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }
    }
    //    把字符串类型的键==转化boolean的数据
    public static boolean getBoolean(HttpServletRequest request,String key)
    {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }
    //    把字符串类型的键==转化String的数据==(最常用的)
    public static String getString(HttpServletRequest request,String key)
    {
        try {
            String result = request.getParameter(key);
            if (result != null)
            {
                result = result.trim();
            }
            if ("".equals(result))
            {
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}