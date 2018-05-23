package com.linekong.report.reyun.util;

import com.google.gson.Gson;

public final class JsonUtil {
          
   /**
    * Object 转换为json
    * @param Object obj
    * 				obj对象
    * @return String json
    */
   public static String convertBeanToJson(Object obj){
	   return obj == null ? null : new Gson().toJson(obj); 
   }
   /**
    * gson进行json转换对象
    * @param String json
    * 				json格式数据
    * @param Class  clazz
    * 				需要转换的对象
    */
   public static <T> T convertJsonToBean(String json,Class<? extends T> clazz){
	   Gson gson = new Gson();
	   return  json == null ? null : gson.fromJson(json, clazz); 
   }

}
