package com.linekong.game.activity.utils;

public class SysCodeConstant {

    //配置相关的key
    // ** begin* 短信验证相关 ****
    public static final String SEND_MESSAGE_URL           = "phone.messege.url";           //短信服务商url
    public static final String SEND_MESSAGE_ACOUNT        = "phone.messege.account";       //账号
    public static final String SEND_MESSAGE_PASSWORD      = "phone.messege.password";      //密码
    public static final String SEND_MESSAGE_SIGN_KEY      = "phone.messege.sign";          //签名
    public static final String SEND_MESSAGE_CONTENT       ="phone.messege.content";        //发送短信内容
    public static final String EXPIRE_REDIS_VALIDATE_CODE = "redis.validate.code.expire";  //设置验证码保存在redis里的过期时间
    public static final String LENGTH_VALIDATE_CODE       = "length.validate.code";        //验证码长度
    // ** end*   短信验证相关 ****

    //** begin*  xmlRpc相关的配置
    public static final String INVOKE_XML_RPC_ADDRESS     = "xml_rpc_address"; //xmlrpc 调用地址
    public static final String INVOKE_XML_RPC_KEY         = "xml_rpc_key"; //xmlrpc调用key值
    //** end*   xmlRpc相关的配置

    //** begin* 赠送奖励活动相关的配置
    public static final String NNTG_ACT1_GIVE_ITEM        = "nntg.giveItem.act1";//闹闹天宫的赠送活动1（绑定者活动）
    public static final String NNTG_ACT2_GIVE_ITEM        = "nntg.giveItem.act2";//闹闹天宫的赠送活动2（绑定者绑定后发给邀请者的奖励）
    public static final String MAX_SEND_COUNT             = "max.send.count";  //最大的重新发送次数
    //** end*   赠送活动相关的配置


    //**********       状态码    ********
    public static final int					DEAL_SUCCESS							= 1;		 //执行成功状态吗
    public static final int					ERROR_SYSTEM					        = -200;		 //系统异常
    public static final int					HTTP_EXCEPION					        = -201;		 //http请求异常
    public static final int                 ERROR_PARAM                             = -1001;     //请求参数格式错误
    public static final int                 ERROR_PHONE_NUM                         = -1002;     //手机号不合法
    public static final int                 ERROR_SEND_PHONE_MESSAGE                = -1003;    //请求服务商发送验证码失败
    public static final int                 ERROR_PHONE_REPEAT_BIND                 = -1004;    //账号已经绑定过了
    public static final int                 ERROR_PHONE_CODE_MATCH                  = -1005;    //验证码输入错误
    public static final int                 ERROR_PHONE_CODE_UNEXIST                = -1006;    //无效的验证码(过期或服务器被刷)
    public static final int                 ERROR_PROPERTY_CONFIG                   = -1007;    //参数未配置
    public static final int                 ERROR_CONFIG_VALIDATE_LENGTH            = -1008;    //验证码长度未配置
    public static final int                 ERROR_CONFIG_VALIDATE_EXPIRED           = -1009;    //验证码过期时间未配置
    public static final int                 ERROR_CONCURRENT_RQUEST                 = -1010;    //验证码有效时间内有重复请求，包括并发请求
    public static final int                 ERROR_SIGN                              = -1011;    //验签失败
    public static final int                 ERROR_INVITED_REPEAT                    = -1012;    //该用户已经被邀请
    public static final int                 ERROR_ENCAPSULATE_PARAM                 = -1013;    //参数封装错误（基本不会）
    public static final int                 ERROR_PHONE_NOT_BIND                    = -1014;    //账号未绑定
    public static final int                 INVOKE_XML_RPC_ERROR                    = -1200;    //xmlRpc错误
    public static final int                 ERROR_OTHER                             = -1500;    //其它错误

    //**********    业务中需要用到的一些常量  *********
    public static final String DEL_SEND_MESSAGE_SUCCESS        = "success";


    //**********    存redis使，通常将key设置一个前缀 ***********************
    public static final String REDIS_INCR_PREFIX               = "incr_";//设置redis保存增量的前缀

    public static final String PREVENT_CONCURRENT_CHECK_CODE   = "checkPhoneCode_concurrent_";//接口防并发存redis的key前缀

    public static final String REDIS_MESSGAGE_PREFIX           = "message_";//设置redis保存短信验证码的前缀

    public static final String REDIS_BINDED_PHONE              = "binded_phoneNum_";//存放已绑定的手机号的key前缀

    public static final String REDIS_INVITED_PHONE             = "invited_phoneNum_";//存放已被邀请过的手机号（被邀请者）

    public static final String REDIS_TEMP_KEY                  = "temp_set_key_";//临时使用的redis的key，用于上报通讯录取交集

}
