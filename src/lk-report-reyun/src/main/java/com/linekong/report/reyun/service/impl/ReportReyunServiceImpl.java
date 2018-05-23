package com.linekong.report.reyun.service.impl;

import com.linekong.report.reyun.dao.model.EratingDao;
import com.linekong.report.reyun.dao.model.LogReportReyunFailDao;
import com.linekong.report.reyun.dao.model.LogReportReyunDao;
import com.linekong.report.reyun.pojo.ChargeInfoFormBean;
import com.linekong.report.reyun.pojo.ReportChargeInfoFormBean;
import com.linekong.report.reyun.pojo.VO.LogReportReyunFailVO;
import com.linekong.report.reyun.pojo.VO.LogReportReyunVO;
import com.linekong.report.reyun.pojo.VO.UmsUserVO;
import com.linekong.report.reyun.service.ReportReyunChargeThread;
import com.linekong.report.reyun.service.ReportReyunService;
import com.linekong.report.reyun.util.Common;
import com.linekong.report.reyun.util.Constant;
import com.linekong.report.reyun.util.ThreadPoolUtil;
import com.linekong.report.reyun.util.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.linekong.report.reyun.util.HttpUtils.httpPost1;

@Service
public class ReportReyunServiceImpl implements ReportReyunService {
	@Autowired
	public EratingDao eratingDao;
	@Autowired
	public LogReportReyunFailDao logReportReyunFailDao;
	@Autowired
	public LogReportReyunDao logReportReyunDao;
	/**
	 * 通知热云充值信息
	 * @param lkChannelId
	 * @param requestIp
	 * @return
	 */
	public  int reportReyunWithCharge(ChargeInfoFormBean form, String requestIp) {
		try {
			UmsUserVO umsUser = new UmsUserVO();
			//获取广告ID
			if (!"".equals(form.getUserId()) && null != form.getUserId()) {
				umsUser = eratingDao.getEquipmentIdById(form.getUserId(), Constant.UMS_USER + form.getGameId());
			} else {
				umsUser = eratingDao.getEquipmentIdByName(form.getUserName(), Constant.UMS_USER + form.getGameId());
			}
			//判断是否存在该账号
			if(umsUser == null){
				return Constant.USER_NOT_EXIST;
			}
			//如果广告ID为0或者空，则说明，该用户不是广告用户，则无需发送热云
			if (null == umsUser.getAdId() || "".equals(umsUser.getAdId()) || "0".equals(umsUser.getAdId())) {
				return Constant.SUCCESS;
			}
			//获取应用appkey
			String appKey = Constant.APP_ID_MHJ;
			//查询是否重复发送
			int reportCount = logReportReyunDao.selectCountLog(form.getChargeDetailId(), Constant.TYPE_CHARGE);
			// 如果不重复，单起线程，向热云发送充值信息
			if (reportCount <= 0) {
				String url = Common.getNoticURL(Constant.TYPE_CHARGE);
				//生成需要发送的信息
				ReportChargeInfoFormBean paramBean = new ReportChargeInfoFormBean(appKey, umsUser.getAdId(), form);
				//生成记录信息
				LogReportReyunVO logReportReyunVO = new LogReportReyunVO(form.getChargeDetailId(), Constant.TYPE_CHARGE,requestIp,form.getGameId(),Constant.NOT_REPORT);
				LogReportReyunFailVO logReportReyunFailVO = new LogReportReyunFailVO(form.getChargeDetailId(), Constant.TYPE_CHARGE, paramBean.toString(),requestIp,form.getGameId());
				//向热云发送充值信息
				ThreadPoolUtil.pool.submit(new ReportReyunChargeThread(url, paramBean.toString(), logReportReyunVO, logReportReyunFailVO,
						logReportReyunDao, logReportReyunFailDao));

			}
			return Constant.SUCCESS;
		}catch(Exception e){
			LoggerUtil.error(this.getClass(), "receive report ReyunWithCharge error:"+form.toString()+",error info:"+e.toString());
			return Constant.ERROR;
		}
	}
   
}
