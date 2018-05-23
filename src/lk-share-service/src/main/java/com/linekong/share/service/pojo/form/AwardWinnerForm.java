package com.linekong.share.service.pojo.form;

import com.linekong.share.service.utils.annotation.DataValidate;
import com.linekong.share.service.utils.annotation.RegexType;

/**
 * 获得奖励的用户
 */
public class AwardWinnerForm {
    @DataValidate(description = "活动号", nullable = false)
    private String actId;
    @DataValidate(description = "账号", nullable = false)
    private String passportName;
    @DataValidate(description = "游戏Id", nullable = false, regexType = RegexType.NUMBER)
    private String gameId;
    @DataValidate(description = "区服Id", nullable = false)
    private String gatewayId;
    @DataValidate(description = "角色Id", nullable = true, regexType = RegexType.NUMBER)
    private String roleId;
    @DataValidate(description = "签名", nullable = false)
    private String sign;

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("actId=").append(actId);
        sb.append(",passportName=").append(passportName);
        sb.append(",gameId=").append(gameId);
        sb.append(",gatewayId=").append(gatewayId);
        sb.append(",roleId=").append(roleId);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
