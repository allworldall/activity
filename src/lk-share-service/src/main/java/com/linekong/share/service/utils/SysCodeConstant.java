package com.linekong.share.service.utils;

public class SysCodeConstant {
	
	
	public static final String SHARE_UPPER_LIMIT    = "share_upper_limit";  //分享上限
	
	public static final String REDIS_KEY_SHARE_NUM = "share_num_"; //Redis分享总数前缀
	
	public static String REDIS_KEY_SHARE_LOG = "share_click_log_"; //分享信息点击记录

	public static String REDIS_KEY_SHARE_USER_LOG = "share_userinfo_log_"; //分享用户本身信息记录
	
	public static final String SHARE_USER_INFO_UNIQULYID = "uniqulyId";
	
	public static final String SHARE_USER_INFO_GAMEID = "gameId";
	
	public static final String SHARE_USER_INFO_GATEWAYID = "gatewayId";
	
	public static final String SHARE_USER_INFO_ACTIVITYID = "activityId";
	
	public static final String SHARE_USER_INFO_PASSPORTNAME = "passportName";
	
	public static final String REDIS_KEY_EXPIRE_TIME = "redis_key_expire"; //单位为秒
	
	public static final String INVOKE_XML_RPC_ADDRESS = "xml_rpc_address"; //xmlrpc 调用地址
	
	public static final String INVOKE_XML_RPC_KEY = "xml_rpc_key"; //xmlrpc调用key值

	public static final String REDIS_KEY_SHORT_URL = "redis_short_url_";

	public static final String SINA_SHORTURL_APPKEY = "sina_shortUrl_appkey";//长连接转短连接api的appkey

	public static final String SINA_SHORTURL_ADDRESS = "sina_shortUrl_address";//长连接转短连接api的请求地址

	public static final String SINA_SHORTURL_SWITCH = "sina_shortUrl_switch";//长连接转短连接服务设置一个开关





	//************** begin 开关型常量   如要定义开关型常量，可在此处定义 **************************************
//	public static final String SWITCH_OPEN = "on";

	public static final String SWITCH_CLOSE = "close";
	//************** end 开关型常量    **************************************


	public static final int SUCCESS = 1; //成功
	
	public static final int MET_CONDITION = 2; //已经满足条件
	
	public static final int ALREADY_CLICK = 3; //已经点击过

	public static final int PARAM_ERROR = -100; //参数错误
	
	public static final int SIGN_ERROR = -101; //sign错误
	
	public static final int UNIQULYID_ERROR = -102; //唯一标识不存在

	public static final int ORIGINALURL_EMPTY = -103;//传入的原始连接为空

	public static final int CONFIG_SHORT_URL_ERROR = -104;//长连接转短连接功能开关未配置或配置有误

	public static final int DB_ERROR = -200; //数据库错误
	
	public static final int REDIS_ERROR = -300; //Redis异常
	
	public static final int INVOKE_XML_RPC_ERROR = -500;

	public static final int SYSTEM_ERROR = -600; //系统错误



}
