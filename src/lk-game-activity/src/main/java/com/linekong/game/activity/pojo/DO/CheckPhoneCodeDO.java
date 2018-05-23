package com.linekong.game.activity.pojo.DO;

import com.linekong.game.activity.utils.annotation.DataValidate;
import com.linekong.game.activity.utils.annotation.RegexType;

/**
 * 校验验证码是否正确的接口接收参数实体类
 */
public class CheckPhoneCodeDO {
    @DataValidate(description = "手机号", nullable = false, length = 11, regexType = RegexType.PHONENUMBER)
    private String phoneNum;
    @DataValidate(description = "应用Id，一款游戏对应一个", nullable = false, length = 8, regexType = RegexType.NUMBER)
    private String appId;
    @DataValidate(description = "游戏Id", nullable = false, regexType = RegexType.NUMBER)
    private String gameId;
    @DataValidate(description = "角色Id", nullable = false, regexType = RegexType.NUMBER)
    private String roleId;
    @DataValidate(description = "手机验证码", nullable = false, length = 6)
    private String phoneCode;
    @DataValidate(description = "用户账号", nullable = false, maxLength = 50)
    private String account;
    @DataValidate(description = "账号类型", nullable = false, length = 1)
    private String accountType;
    @DataValidate(description = "网关/区服Id", nullable = false, regexType = RegexType.NUMBER)
    private String gatewayId;
    @DataValidate(description = "签名", nullable = false)
    private String sign;
    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public CheckPhoneCodeDO() {
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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
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
        sb.append(",gameId=").append(gameId);
        sb.append(",roleId=").append(roleId);
        sb.append(",phoneCode=").append(phoneCode);
        sb.append(",account=").append(account);
        sb.append(",accountType=").append(accountType);
        sb.append(",gatewayId=").append(gatewayId);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
