package com.CNFloWopen.niugou.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaiduDwz {
        final static String CREATE_API = "https://dwz.cn/admin/v2/create";
        final static String TOKEN = "c37b738713ddd7d9a2c2c85ff547cef8"; // TODO:设置Token
        private static Logger log = LoggerFactory.getLogger(ShortNetAddressUtil.class);
        public static int TIMEOUT = 30*1000;  //30秒
        class UrlResponse {
            @SerializedName("Code")
            private int code;
    
            @SerializedName("ErrMsg")
            private String errMsg;
    
            @SerializedName("LongUrl")
            private String longUrl;
    
            @SerializedName("ShortUrl")
            private String shortUrl;
    
            public int getCode() {
                return code;
            }
    
            public void setCode(int code) {
                this.code = code;
            }
    
            public String getErrMsg() {
                return errMsg;
            }
    
            public void setErrMsg(String errMsg) {
                this.errMsg = errMsg;
            }
    
            public String getLongUrl() {
                return longUrl;
            }
    
            public void setLongUrl(String longUrl) {
                this.longUrl = longUrl;
            }
    
            public String getShortUrl() {
                return shortUrl;
            }
    
            public void setShortUrl(String shortUrl) {
                this.shortUrl = shortUrl;
            }
        }
    
        /**
         * 创建短网址
         *
         * @param longUrl
         *            长网址：即原网址
         * @return  成功：短网址
         *          失败：返回空字符串
         */
        public static String createShortUrl(String longUrl) {
            String params = "{\"url\":\""+ longUrl + "\"}";
    
            BufferedReader reader = null;
            try {
                // 创建连接
                URL url = new URL(CREATE_API);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setInstanceFollowRedirects(true);
                //            不使用缓存
                connection.setUseCaches(false);
//              设置连接超时的时间
                connection.setConnectTimeout(TIMEOUT);
                connection.setRequestMethod("POST"); // 设置请求方式
                connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
                connection.setRequestProperty("Token", TOKEN); // 设置发送数据的格式");
    
                // 发起请求
                connection.connect();
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
                out.append(params);
                out.flush();
                out.close();
    
                // 读取响应
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                String res = "";
                while ((line = reader.readLine()) != null) {
                    res += line;
                }
                reader.close();
    
                // 抽取生成短网址
                UrlResponse urlResponse = new Gson().fromJson(res, UrlResponse.class);
                if (urlResponse.getCode() == 0) {
                    return urlResponse.getShortUrl();
                } else {
                    System.out.println(urlResponse.getErrMsg());
                }
    
                return ""; // TODO：自定义错误信息
            } catch (IOException e) {
                // TODO
                e.printStackTrace();
            }
            return ""; // TODO：自定义错误信息
        }
    
        public static void main(String[] args) {
            String res = createShortUrl("https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login");
            System.out.println(res);
    
        }
    
    }