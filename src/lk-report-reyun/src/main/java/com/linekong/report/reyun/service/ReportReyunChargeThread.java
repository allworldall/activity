package com.linekong.report.reyun.service;

import com.linekong.report.reyun.dao.model.LogReportReyunFailDao;
import com.linekong.report.reyun.dao.model.LogReportReyunDao;
import com.linekong.report.reyun.pojo.ReportReyunResultFormBean;
import com.linekong.report.reyun.pojo.ReportReyunResultFormBean;
import com.linekong.report.reyun.pojo.VO.LogReportReyunFailVO;
import com.linekong.report.reyun.pojo.VO.LogReportReyunVO;
import com.linekong.report.reyun.util.Constant;
import com.linekong.report.reyun.util.log.LoggerUtil;

import static com.linekong.report.reyun.util.HttpUtils.*;

public class ReportReyunChargeThread implements Runnable{
	//推送地址
	private String noticeURL;
	//推送参数
	private String param;
	//消息数据库对应
	private LogReportReyunVO logReportReyunVO;
	//消息推送失败数据库对应
	private LogReportReyunFailVO logReportReyunFailVO;

	private LogReportReyunFailDao logReportReyunFailDao;

	private LogReportReyunDao logReportReyunDao;
	
	public ReportReyunChargeThread(String noticeURL, String param, LogReportReyunVO logReportReyunVO, LogReportReyunFailVO logReportReyunFailVO,
								   LogReportReyunDao logReportReyunDao, LogReportReyunFailDao logReportReyunFailDao){
		this.noticeURL = noticeURL;
		this.param = param;
		this.logReportReyunVO = logReportReyunVO;
		this.logReportReyunFailVO = logReportReyunFailVO;
		this.logReportReyunDao = logReportReyunDao;
		this.logReportReyunFailDao = logReportReyunFailDao;
	}
	
	@Override
	public void run() {
		try {
			//记录发送信息，状态为，未发送
			logReportReyunDao.insertLogReportReyun(logReportReyunVO);
			//请求热云
			String noticResult = httpPost1(noticeURL, param);
			ReportReyunResultFormBean resultBean = new ReportReyunResultFormBean(noticResult);

			//查询是否存在失败表中
			int failCount = logReportReyunFailDao.selectCountFailInfo(logReportReyunFailVO.getReportMark(),logReportReyunFailVO.getReportFailType());
			//验证请求热云返回结果
			if(resultBean.getStatus()==0) {
				//成功更新上报信息记录表状态为成功
				logReportReyunVO.setReportState(Constant.REPORT_SUCCESS);
				logReportReyunDao.updateLogReportReyunState(logReportReyunVO);
				//如果存在失败记录表中，删除失败记录表数据
				if(failCount > 0){
					logReportReyunFailDao.deleteLogReportReyunFail(logReportReyunFailVO.getReportMark(),logReportReyunFailVO.getReportFailType());
				}
			}else {
				//成功更新上报信息记录表状态为发送失败
				logReportReyunVO.setReportState(Constant.REPORT_FAIL);
				logReportReyunDao.updateLogReportReyunState(logReportReyunVO);
				//设置失败原因和发送结果
				logReportReyunFailVO.setReportFailInfo(resultBean.getResult().toString(),resultBean.getStatus()+"");
				//处理失败记录表数据
				setLogReportReyunFail(logReportReyunFailVO,failCount);
			}
		}catch (Exception e){
			LoggerUtil.error(ReportReyunChargeThread.class,"report reyun error:"+e.toString()+",param:"+logReportReyunVO.toString());
			//不成功记录到失败表中
			try {
				logReportReyunFailVO.setReportFailInfo(e.toString(),Constant.ERROR+"");
				//查询是否存在失败表中
				int failCount = logReportReyunFailDao.selectCountFailInfo(logReportReyunFailVO.getReportMark(),logReportReyunFailVO.getReportFailType());
				//处理失败记录表数据
				setLogReportReyunFail(logReportReyunFailVO,failCount);
			}catch (Exception e1){
				LoggerUtil.error(ReportReyunChargeThread.class,"log report Reyun fail error:"+e1.toString()+",param:"+logReportReyunFailVO.toString());
			}
		}
	}

	/**
	 * 处理失败记录表数据
	 * 数据不存在则插入，存在则更新发送次数
	 * @param logReportReyunFailVO
	 * @param failCount
	 */
	public void setLogReportReyunFail(LogReportReyunFailVO logReportReyunFailVO ,int failCount){

		if(failCount > 0){
			//如果存在失败记录表中，则更新发送次数
			logReportReyunFailDao.updateLogReportReyunFail(logReportReyunFailVO);
		}else{
			//如果不存在失败记录表中，插入数据
			logReportReyunFailDao.insertLogReportReyunFail(logReportReyunFailVO);
		}
	}
}
