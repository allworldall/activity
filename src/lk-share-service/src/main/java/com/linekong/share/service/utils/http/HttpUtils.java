package com.linekong.share.service.utils.http;

import com.linekong.share.service.utils.log.LoggerUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * 设置超时时间为2秒
     */
    public static String sendGet(String url, String param) {
        long begin = System.currentTimeMillis();
        String result = "";
        BufferedReader br = null;
        HttpURLConnection connection = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            connection =(HttpURLConnection) realUrl.openConnection();
            //设置超时时间，单位：毫秒
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(2000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();
        } catch (Exception e) {
            LoggerUtil.error(HttpUtils.class, "request url=" + url +",param=" + param +",result="+result + ",error info:" + e.toString() + ",time="+(System.currentTimeMillis()-begin));
        }finally {
            LoggerUtil.info(HttpUtils.class, "request url=" + url +",param=" + param +",result="+result + ",time="+(System.currentTimeMillis()-begin));
            if(connection != null) {
                connection.disconnect();
            }
            return result;
        }
    }
}
