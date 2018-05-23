package com.linekong.report.reyun.util;

import com.linekong.report.reyun.util.log.LoggerUtil;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Random;


public class Common {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 获取上报热云的URL
	 * @param type
	 * @return
	 */
	public static String getNoticURL(String type){
		String url = Constant.NOTIC_REYUN_URL;
		switch(type){
			case "1" : url = url + Constant.MATHOD_CHARGE;break;
			default: url = url;
		}
		return url;
	}
	/**
	 * url encode 处理
	 * @param String result	 	需要encode处理的值
	 * @param String charset	字符集
	 * @return 编码后数据
	 */
	public static String urlEncode(String result,String charset){
		try {
			return URLEncoder.encode(result, charset);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(Common.class, e.getMessage());
		}
		return null;
	}

	/**
	 * url decode 处理
	 * @param String result		需要decode处理的值
	 * @param String charset	字符集
	 * @return 解码后数据
	 */
	public static String urlDecode(String result,String charset){
		try {
			return URLDecoder.decode(result, charset);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(Common.class, e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * */
	public static long ChargeDetailIdGenerator() {
		Random r = new Random();
		long timeNow = System.currentTimeMillis();
		return  (timeNow*100 + (long)r.nextInt(99));
	}
	/**
	 * base64解密
	 * @param String str
	 * 				base64加密值
	 * @return base64解密以后值
	 */
	public static String decodeBase64(String str){
		return new String(Base64.decodeBase64(str));
	}
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

	//IP 和int的互转
	public static String iplong2Str(long ip)
	{
		StringBuilder sb = new StringBuilder();
		//直接右移24位
		sb.append(ip >> 24);
		sb.append(".");
		//将高8位置0，然后右移16
		sb.append((ip & 0x00FFFFFF) >> 16);
		sb.append(".");
		//将高16位置0，然后右移8位
		sb.append((ip & 0x0000FFFF) >> 8);
		sb.append(".");
		//将高24位置0
		sb.append((ip & 0x000000FF));
		return sb.toString();
	}

	public static long ipStr2long(String strIp) throws UnknownHostException {
		long[] ip = new long[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		//进行左移位处理
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 获取本机IP
	 */
	public static String getLocalIp() {
		String localIp = "127.0.0.1";
		try {
			InetAddress address = InetAddress.getLocalHost();// 获取的是本地的IP地址
			localIp = address.getHostAddress();// 192.168.0.121

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return localIp;
	}

	public static void main(String[] args) throws UnknownHostException {
		System.out.println(ipStr2long("119.254.200.4"));//2013186052
		System.out.println(ipStr2long("192.168.84.24"));//2013186052    3232257048
		System.out.println(iplong2Str(3232300051L));
		System.out.println(ipStr2long("112.112.40.155"));//1886398619
	}
}
