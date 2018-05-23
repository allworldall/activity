package com.linekong.share.service.pojo.form;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.linekong.share.service.utils.annotation.DataValidate;
import com.linekong.share.service.utils.annotation.RegexType;

public class RecordUserShareInfoForm {

	@DataValidate(description = "唯一标识", nullable = false)
	private String uniqulyId; // 唯一标识
	@DataValidate(description = "活动ID", nullable = false, regexType = RegexType.NUMBER)
	private String activityId; // 活动号
	@DataValidate(description = "游戏ID", nullable = false, regexType = RegexType.NUMBER)
	private String gameId; // 游戏ID
	@DataValidate(description = "区服ID", nullable = false, regexType = RegexType.NUMBER)
	private String gatewayId; // 区服ID
	@DataValidate(description = "通行证账号", nullable = false)
	private String passportName; // 通行证账号
	@DataValidate(description = "加密sign值", nullable = false)
	private String sign; // 加密sign值
	
	private String roleId; //角色ID

	public String getUniqulyId() {
		return uniqulyId;
	}

	public void setUniqulyId(String uniqulyId) {
		this.uniqulyId = uniqulyId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
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

	public String getPassportName() {
		return passportName;
	}

	public void setPassportName(String passportName) {
		this.passportName = passportName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
