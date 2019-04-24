package com.CNFloWopen.niugou.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 检测验证码的工具类
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request)
    {
        String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");
//        equalsIgnoreCase和equals
        if (verifyCodeActual==null || !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected))
        {
            return false;
        }
        return true;
    }
}
