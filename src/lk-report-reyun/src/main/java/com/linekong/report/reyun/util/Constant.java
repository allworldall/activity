package com.linekong.report.reyun.util;

public class Constant {
	//***************************************状态********************************************************//

	public static final int					NOT_REPORT						= 0;		//未完成

	public static final int					REPORT_SUCCESS					= 1;		//成功

	public static final int					REPORT_FAIL					= -1;		//失败，会记录到失败表中

	//***************************************结果码********************************************************//
	public static final int					INIT_RESULT					=  0;		 //初始结果

	public static final int					SUCCESS							=  1;		 //成功

	public static final int 					USER_NOT_EXIST					= -1472;	//账号不存在

	public static final int					ERROR							= -200;	  	//失败

	public static final int					HTTP_EXCEPION					= -201;		 //http请求异常

	public static final int					ERROR_PARAM_VALIDATE			= -21006;	 //参数校验异常

	//***************************************配置信息********************************************************//
	public static final String 				UMS_USER						= "UMS_USER_";					//erating 库中UMS_USER表在echaring库中对应同义词的前缀

	public static final String 				TYPE_CHARGE					= "1";							//类别：充值

	public static final String 				NOTIC_REYUN_URL				= "http://log.reyun.com";		//推送热云地址

	public static final String 				MATHOD_CHARGE					= "/receive/track/payment";	//统计用户充值方法

	public static final String 				APP_ID_MHJ						= "app_id_mhj";				//莽荒纪的APP ID
}
