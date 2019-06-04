package com.CNFloWopen.niugou.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码和二维码工具类
 */
public class CodeUtil {

    /**
     * 检验验证码是否符合
     * @param request
     * @return
     */
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

    /**
     * 生成二维码的图片流
     * @param content
     * @param resp
     * @return
     */
    public static BitMatrix generateQRCodeStream(String content, HttpServletResponse resp)
    {
//        给响应添加头信息，主要是告诉我们浏览器返回的是图片流
//        不要缓存
        resp.setHeader("Cache-Control","no-store");
        resp.setHeader("Pragma","no-cache");

        resp.setDateHeader("Expires",0);
        resp.setContentType("image/png");
//        设置图片的文字编码和内边框距
        Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
//        MARGIN内框边距
        hints.put(EncodeHintType.MARGIN,0);
        BitMatrix bitMatrix;
        try {
//            参数顺序为：编码内容，编码类型，生成图片高度，设置参数
             bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,300,300,hints);
        }catch (WriterException e)
        {
            e.printStackTrace();
            return null;
        }
        return bitMatrix;
    }


}
