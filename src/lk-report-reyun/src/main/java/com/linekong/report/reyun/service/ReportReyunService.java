package com.linekong.report.reyun.service;

import com.linekong.report.reyun.pojo.ChargeInfoFormBean;

public interface ReportReyunService {
	/**
	 * 推送热云充值信息
	 * @param form
	 * @param requestIp
	 * @return
	 */
	public int reportReyunWithCharge(ChargeInfoFormBean form,String requestIp);
}
