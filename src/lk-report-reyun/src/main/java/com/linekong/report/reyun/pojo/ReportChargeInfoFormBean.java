package com.linekong.report.reyun.pojo;


import com.linekong.report.reyun.util.JsonUtil;

/**
 * 通知热云的充值信息
 */
public class ReportChargeInfoFormBean {

    public String appid;        //应用appkey

    public String who;          //账户ID

    public Context context;      //上下文信息(类Context的json格式)

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ReportChargeInfoFormBean(String appId, String deviceid, ChargeInfoFormBean form) {
        this.appid = appId;
        this.who = form.getUserId();
        this.context = new Context(deviceid, form);
    }

    @Override
    public String toString() {
        return JsonUtil.convertBeanToJson(this);
    }
}

/**
 * NoticeChargeInfoFormBean类的详细信息
 */
class Context{

    public String deviceid;          //ios存idfa  android存imei

    public String transactionid;    //交易的流水号

    public String paymenttype;      //支付类型，例如支付宝(alipay)，银联(unionpay)，微信支付（weixinpay）,易宝支付（yeepay），最多16个字符，如果是,系统赠送的，paymentType为：FREE

    public String currencytype;     //货币类型，按照国际标准组织ISO 4217中规范的3位字母，例如CNY人民币、USD美金等

    public String currencyamount;   //支付的真实货币的金额

    public String channelid;         //渠道ID

    public String idfa;              //广告标识

    public String idfv;              //Vindor标示符

    public String imei;              //手机的唯一识别号码

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getCurrencytype() {
        return currencytype;
    }

    public void setCurrencytype(String currencytype) {
        this.currencytype = currencytype;
    }

    public String getCurrencyamount() {
        return currencyamount;
    }

    public void setCurrencyamount(String currencyamount) {
        this.currencyamount = currencyamount;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getIdfv() {
        return idfv;
    }

    public void setIdfv(String idfv) {
        this.idfv = idfv;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Context(String deviceid, ChargeInfoFormBean form) {
        this.deviceid = deviceid;
        this.transactionid = form.getChargeDetailId();
        this.paymenttype = form.getPaymentType();
        this.currencytype = form.getCurrenyType();
        this.currencyamount = form.getChargeMoney();
        this.channelid = "unknown";
        this.idfa = deviceid;
        this.idfv = "unknown";
        this.imei = deviceid;
    }

    @Override
    public String toString() {
        return JsonUtil.convertBeanToJson(this);
    }
}