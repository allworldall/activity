package com.linekong.game.activity.pojo.DO;

import com.linekong.game.activity.utils.annotation.DataValidate;
import com.linekong.game.activity.utils.annotation.RegexType;

/**
 * 当前用户信息
 */
public class CurrenUserDO {

    @DataValidate(description = "用户账号", nullable = false)
    private String account;
    @DataValidate(description = "应用Id，每款游戏唯一", nullable = false, regexType = RegexType.NUMBER, length = 8)
    private String appId;
    @DataValidate(description = "游戏Id", nullable = false, regexType = RegexType.NUMBER)
    private String gameId;
    @DataValidate(description = "角色Id", nullable = false, regexType = RegexType.NUMBER)
    private String roleId;
    @DataValidate(description = "签名", nullable = false)
    private String sign;

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
        sb.append("account=").append(account);
        sb.append(",appId=").append(appId);
        sb.append(",gameId=").append(gameId);
        sb.append(",roleId=").append(roleId);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
