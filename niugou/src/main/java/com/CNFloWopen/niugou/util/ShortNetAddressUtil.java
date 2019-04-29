package com.CNFloWopen.niugou.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * 短链接工具类，由于链接过长会导致二维码生成失败，所以需要转化为短链接
 */
public class ShortNetAddressUtil {
    private static Logger log = LoggerFactory.getLogger(ShortNetAddressUtil.class);
    public static int TIMEOUT = 30*1000;  //30秒
    public static String ENCODING = "UTF-8";

    /**
     * 根据传入的url，通过访问百度短视频的接口，将其转化为短的url
     * @param originURL
     * @return
     */
    public static String getShortURL(String originURL)
    {
        String tinyUrl = null;
        try {
//            指定百度短视频的接口
            URL url = new URL("http://dwz.cn/create.php");
//            建立链接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            设置链接的参数
//            使用链接进行输出
            connection.setDoOutput(true);
//            使用链接进行输入
            connection.setDoInput(true);
//            不使用缓存
            connection.setUseCaches(false);
//            设置连接超时的时间
            connection.setConnectTimeout(TIMEOUT);
//            设置请求的方式
            connection.setRequestMethod("POST");
//            设置post的信息，这里为传入的原始的url
            String postData = URLEncoder.encode(originURL.toString(),"utf-8");
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setRequestProperty("Token", "c37b738713ddd7d9a2c2c85ff547cef8"); // 设置发送数据的格式");
//            输出原始的url
            connection.getOutputStream().write(("url="+postData).getBytes());
//            连接百度短视频的接口
            connection.connect();
//            获取返回的字符串
            String responseStr = getResponseStr(connection);
            log.info("response str:"+responseStr);
//            在字符串里获取tinyurl,即短链接
            tinyUrl = getValueByKey(responseStr,"tinyurl");
            log.info("tinyurl:"+tinyUrl);
//            关闭链接
            connection.disconnect();
        }catch (IOException e)
        {
            log.error("getShortUrl error:"+e.getMessage());
        }
        return tinyUrl;
    }

    /**
     * 通过HttpURLConnection获取返回的字符串
     * @param connection
     * @return
     * @throws IOException
     */
    private static String getResponseStr(HttpURLConnection connection) throws IOException {
        StringBuffer result = new StringBuffer();
//        从链接中获取http的状态码
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK)
        {
//            如果返回的值是ok，那么取出连接的输入流
            InputStream in = connection.getInputStream();
//            BufferedReader就是一行一行去读取的
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,ENCODING));
            String inputLine = "";
            while ((inputLine = reader.readLine())!=null)
            {
//                将消息逐行加入到结果中
                result.append(inputLine);
            }
        }
//        将结果转为string并返回
        return String.valueOf(result);
    }

    /**
     * JSON依据传入的key，获取相对应的value
     * @param replyText
     * @param key
     * @return
     */
    private static String getValueByKey(String replyText,String key)
    {
        ObjectMapper mapper = new ObjectMapper();
//        定义json节点
        JsonNode node;
        String targetValue = null;
        try {
//            把调用返回的消息串转化为json对象
            node = mapper.readTree(replyText);
//            依据key从json对象里获取对应的值
            targetValue = node.get(key).asText();
        }catch (JsonProcessingException e)
        {
            log.error("getValueByKey error:"+e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("getValueByKey error:"+e.toString());
        }
        return targetValue;
    }

    /**
     * 验证百度接口是否实现
     * @param args
     */
    public static void main(String[] args) {
        getShortURL("https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login");
    }
}
