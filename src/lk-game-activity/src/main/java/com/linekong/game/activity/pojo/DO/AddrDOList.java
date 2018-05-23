package com.linekong.game.activity.pojo.DO;

import com.linekong.game.activity.utils.annotation.DataValidate;
import com.linekong.game.activity.utils.annotation.RegexType;

import java.util.List;

/**
 * 将用户通讯录放入一个list中
 */
public class AddrDOList {

    @DataValidate(description = "应用Id，每款游戏唯一", nullable = false, regexType = RegexType.NUMBER, length = 8)
    private String appId;
    @DataValidate(description = "游戏Id", nullable = false, regexType = RegexType.NUMBER)
    private String gameId;
    @DataValidate(description = "账号", nullable = false, maxLength = 50)
    private String account;
    @DataValidate(description = "签名", nullable = false)
    private String sign;

    private List<AddrDO> data;

    public AddrDOList() {
    }

    public List<AddrDO> getData() {
        return data;
    }

    public void setData(List<AddrDO> data) {
        this.data = data;
    }
    /*public void setData(String data) {
        this.data = JsonUtil.jsonToList(data, AddrDO.class);
    }*/

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
        sb.append("data=").append("不显示通讯录信息");
        sb.append(",appId=").append(appId);
        sb.append(",gameId=").append(gameId);
        sb.append(",account=").append(account);
        sb.append(",sign=").append(sign);
        return sb.toString();
    }
}
