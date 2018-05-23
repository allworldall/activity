package com.linekong.share.service.pojo.form;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.linekong.share.service.utils.annotation.DataValidate;

public class ClickShareInfoRecordForm {
    @DataValidate(description = "唯一标识", nullable = false)
    private String uniqulyId;      //唯一标识
	
	private String clickIp;        //点击IP字符串
	
	private String clickUa;        //用户点击获取到浏览器user-agent
	
	private String clickUrl;       //点击URL地址
	
	@DataValidate(description = "加密sign值", nullable = false)
	private String sign;           //加密sign值

	public String getUniqulyId() {
		return uniqulyId;
	}

	public void setUniqulyId(String uniqulyId) {
		this.uniqulyId = uniqulyId;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
