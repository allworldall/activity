package com.linekong.game.activity.pojo.DO;

import com.linekong.game.activity.utils.annotation.DataValidate;
import com.linekong.game.activity.utils.annotation.RegexType;

/**
 * 发送验证码接口，接收参数实体类
 */
public class SendPhoneCodeDO {
    @DataValidate(description = "手机号", nullable = false ,length = 11, regexType = RegexType.PHONENUMBER)
    private String phoneNum;
    @DataValidate(description = "游戏Id", nullable = false, length = 8,  regexType = RegexType.NUMBER)
    private String appId;
    @DataValidate(description = "签名", nullable = false)
    private String sign;

    public SendPhoneCodeDO() {
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("phoneNum=").append(phoneNum);
        sb.append(",appId=").append(appId);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
