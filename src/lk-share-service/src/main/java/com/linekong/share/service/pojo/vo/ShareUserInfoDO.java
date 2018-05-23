package com.linekong.share.service.pojo.vo;

public class ShareUserInfoDO {
	
	private String uniqulyId;    //唯一标识
	
	private String passportName; //账号
	
	private int gameId;          //游戏账号
	
	private int gatewayId;       //区服ID
	
	private int activityId;      //活动号
	
	private int clickNum;        //点击数
	
	private int logId;           //自增ID
	
	private int roleId;          //角色ID

	public String getUniqulyId() {
		return uniqulyId;
	}

	public void setUniqulyId(String uniqulyId) {
		this.uniqulyId = uniqulyId;
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

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
