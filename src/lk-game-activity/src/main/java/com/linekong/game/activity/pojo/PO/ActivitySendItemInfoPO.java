package com.linekong.game.activity.pojo.PO;
import com.linekong.game.activity.pojo.DO.CheckPhoneCodeDO;

//发送物品实体类
public class ActivitySendItemInfoPO {
    private int logId;			  //记录ID
    private String activityId;		//活动ID
    private String passportName;   //用户账号
    private int gameId;            //游戏ID
    private int gatewayId;         //网关ID
    private String itemCode;       //物品代码
    private int itemNum;           //物品数量
    private long roleId;            //角色ID

    public ActivitySendItemInfoPO() {
    }
    //封装发送奖励信息
    public ActivitySendItemInfoPO(ActRewardInfoPO actRewardInfoPO, ItemInfoPO itemInfoPO) {
        this.activityId = itemInfoPO.getActivityId();
        this.passportName = actRewardInfoPO.getPassportName();
        this.gameId = actRewardInfoPO.getGameId();
        this.gatewayId = actRewardInfoPO.getGatewayId();
        this.itemCode = itemInfoPO.getItemCode();
        this.itemNum = itemInfoPO.getItemNum();
        this.roleId = actRewardInfoPO.getRoleId();
    }
    //封装发送奖励信息
    public ActivitySendItemInfoPO(CheckPhoneCodeDO checkPhoneCode, ItemInfoPO itemInfoPO) {
        this.activityId = itemInfoPO.getActivityId();
        this.passportName = checkPhoneCode.getAccount();
        this.gameId = Integer.parseInt(checkPhoneCode.getGameId());
        this.gatewayId = Integer.parseInt(checkPhoneCode.getGatewayId());
        this.itemCode = itemInfoPO.getItemCode();
        this.itemNum = itemInfoPO.getItemNum();
        this.roleId = Long.parseLong(checkPhoneCode.getRoleId());
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
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
        sb.append("logId=").append(logId);
        sb.append(",activityId=").append(activityId);
        sb.append(",passportName=").append(passportName);
        sb.append(",gameId=").append(gameId);
        sb.append(",gatewayId=").append(gatewayId);
        sb.append(",itemCode=").append(itemCode);
        sb.append(",itemNum=").append(itemNum);
        sb.append(",roleId=").append(roleId);
        return sb.toString();
    }
}
