package com.linekong.share.service.utils.function;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.linekong.share.service.utils.DataFormatUtils.StringUtils;
import com.linekong.share.service.utils.SysCodeConstant;
import com.linekong.share.service.utils.cache.CommonCache;
import com.linekong.share.service.utils.http.HttpUtils;
import com.linekong.share.service.utils.log.LoggerUtil;

import java.net.URLEncoder;

/**
 * 此类用于将长连接转换成短连接，
 * 采用新浪的api
 */
public class CreateShortUrlFunction {

    public static String getShortUrl(String originalUrl) {
        try {
            String appkey = (String) CommonCache.getConfigInfo(SysCodeConstant.SINA_SHORTURL_APPKEY);
            String address = (String) CommonCache.getConfigInfo(SysCodeConstant.SINA_SHORTURL_ADDRESS);
            if (StringUtils.isEmpty(appkey) || StringUtils.isEmpty(address)) {
                LoggerUtil.error(CreateShortUrlFunction.class, "request sina api appkey or address not config");
                return null;
            } else {
                //拼接get请求的参数
                StringBuffer buffer = new StringBuffer();
                buffer.append("source=").append(appkey);
                buffer.append("&url_long=").append(URLEncoder.encode(originalUrl, "UTF-8"));
                //调用新浪api
                String responseString = HttpUtils.sendGet(address, buffer.toString());
                //返回结果解析
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(responseString);
                JsonArray jsonArray = element.getAsJsonArray();
                //因为此接口只转一个地址，所以不用遍历
                JsonElement je = jsonArray.get(0);
                JsonObject jobj = je.getAsJsonObject();//从json元素转变成json对象
                return jobj.get("url_short").getAsString();
            }
        }catch (Exception e){
            LoggerUtil.error(CreateShortUrlFunction.class, "long url convert to short url error, error info:"+e.toString());
            return null;
        }
    }

}
