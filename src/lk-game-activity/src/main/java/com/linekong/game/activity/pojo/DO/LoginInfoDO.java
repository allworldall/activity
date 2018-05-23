package com.linekong.game.activity.pojo.DO;

import com.linekong.game.activity.utils.annotation.DataValidate;
import com.linekong.game.activity.utils.annotation.RegexType;

public class LoginInfoDO {
    @DataValidate(description = "用户账号",nullable = false, maxLength = 50)
    private String account;
    @DataValidate(description = "应用Id，一款游戏是唯一的", nullable = false,  length = 8, regexType = RegexType.NUMBER)
    private String appId;
    @DataValidate(description = "游戏Id", nullable = false, regexType = RegexType.NUMBER)
    private String gameId;
    @DataValidate(description = "角色Id", nullable = false, regexType = RegexType.NUMBER)
    private String roleId;
    @DataValidate(description = "账号类型", nullable = false, length = 1)
    private String accountType;
    @DataValidate(description = "账号类型", nullable = true)
    private String phoneNum;
    @DataValidate(description = "签名", nullable = false)
    private String sign;

    public LoginInfoDO() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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
        sb.append("account=").append(account);
        sb.append(",appId=").append(appId);
        sb.append(",gameId=").append(gameId);
        sb.append(",roleId=").append(roleId);
        sb.append(",accountType=").append(accountType);
        sb.append(",phoneNum=").append(phoneNum);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }

    public void packageData(CheckPhoneCodeDO checkPhoneCode) {
        account = checkPhoneCode.getAccount();
        appId = checkPhoneCode.getAppId();
        gameId = checkPhoneCode.getGameId();
        roleId = checkPhoneCode.getRoleId();
        accountType = checkPhoneCode.getAccountType();
    }
}
