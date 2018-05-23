package com.linekong.game.activity.utils;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Common {

    /**
     * 获取真实IP
     * @param request
     * @return
     */
    public static String getTrueIP(HttpServletRequest request){
        //因原方式获取到的是nginx转发的服务器Ip，所以将ip设置为nginx配置在Header中的实际用户的值
        String ip="";
        String xRealIp = request.getHeader("X-Real-IP") ;
        String XForwardedIp = request.getHeader("X-Forwarded-For") ;
        if(XForwardedIp != null && !"".equals(XForwardedIp)){
            ip = XForwardedIp.split(",")[0];
        }else if(xRealIp != null && !"".equals(xRealIp)){
            ip = xRealIp;
        }else{
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 生成短信验证码
     * @param Integer num
     *                生成短信验证码长度
     * @return String
     *         返回生成的短信验证码
     */
    public static String getPhoneCode(int num){
        String code = "";
        for (int i = 0; i < num; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            code += String.valueOf(c);
        }
        return code;
    }
    //产生区间随机数
    private static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

    /**
     * 创建一个高并发下很难重复的数字
     * @return
     */
    public static String createString(){
        int random = new Random().nextInt(10000);
        SimpleDateFormat sf = new SimpleDateFormat("mmss");
        return sf.format(new Date()) + random;
    }

    public static void main(String[] args) {
        System.out.println(createString());
    }
}
