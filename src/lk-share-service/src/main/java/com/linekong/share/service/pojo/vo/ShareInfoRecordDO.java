package com.linekong.share.service.pojo.vo;

public class ShareInfoRecordDO {
	
	private String uniqulyId;      //唯一标识
	
	private long   clickIpNum;     //IP转换为数字
	
	private String clickIp;        //点击IP字符串
	
	private String clickUa;        //用户点击获取到浏览器user-agent
	
	private String clickUrl;       //点击URL地址
	
	private int gameId;            //游戏ID
	
	private int count;
	
	private int activityId;        //活动号
	
	private String passportName;   //玩家账号
	
	private int gatewayId;      //区服ID
	
	private int roleId; //角色ID
	

	public String getUniqulyId() {
		return uniqulyId;
	}

	public void setUniqulyId(String uniqulyId) {
		this.uniqulyId = uniqulyId;
	}

	public long getClickIpNum() {
		return clickIpNum;
	}

	public void setClickIpNum(long clickIpNum) {
		this.clickIpNum = clickIpNum;
	}

	public String getClickIp() {
		return clickIp;
	}

	public void setClickIp(String clickIp) {
		this.clickIp = clickIp;
	}

	public String getClickUa() {
		return clickUa;
	}

	public void setClickUa(String clickUa) {
		this.clickUa = clickUa;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getPassportName() {
		return passportName;
	}

	public void setPassportName(String passportName) {
		this.passportName = passportName;
	}

	public int getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(int gatewayId) {
		this.gatewayId = gatewayId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
