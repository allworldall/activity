package com.linekong.game.activity.pojo.PO;

import java.util.Date;
import java.util.List;

/**
 * 赠送活动奖励实体类
 */
public class ActRewardInfoPO {
    private String passportName;   //用户账号
    private int gameId;            //游戏Id
    private int gatewayId;         //区服Id
    private long roleId;           //角色Id

    public ActRewardInfoPO() {
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(int gatewayId) {
        this.gatewayId = gatewayId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("passportName=").append(passportName);
        sb.append(",gameId=").append(gameId);
        sb.append(",gatewayId=").append(gatewayId);
        sb.append(",roleId=").append(roleId);
        return sb.toString();
    }
}
