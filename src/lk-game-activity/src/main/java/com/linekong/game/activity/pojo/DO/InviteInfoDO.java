package com.linekong.game.activity.pojo.DO;

import com.linekong.game.activity.utils.annotation.DataValidate;
import com.linekong.game.activity.utils.annotation.RegexType;

public class InviteInfoDO {
    @DataValidate(description = "邀请人账号", nullable = false, maxLength = 50)
    private String account;
    @DataValidate(description = "应用Id", nullable = false, regexType = RegexType.NUMBER, length = 8)
    private String appId;
    @DataValidate(description = "游戏Id", nullable = false, regexType = RegexType.NUMBER)
    private String gameId;
    @DataValidate(description = "网关Id", nullable = false, regexType = RegexType.NUMBER)
    private String gatewayId;
    @DataValidate(description = "角色Id", nullable = false, regexType = RegexType.NUMBER)
    private String roleId;
    @DataValidate(description = "被邀请人手机号", nullable = false, regexType = RegexType.PHONENUMBER)
    private String invitedNum;
    @DataValidate(description = "签名", nullable = false)
    private String sign;

    public InviteInfoDO() {
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

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getInvitedNum() {
        return invitedNum;
    }

    public void setInvitedNum(String invitedNum) {
        this.invitedNum = invitedNum;
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
        sb.append(",gatewayId=").append(gatewayId);
        sb.append(",roleId=").append(roleId);
        sb.append(",invitedNum=").append(invitedNum);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
