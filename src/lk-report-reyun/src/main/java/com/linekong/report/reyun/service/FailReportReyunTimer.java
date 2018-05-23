package com.linekong.report.reyun.service;

import com.linekong.report.reyun.dao.model.LogReportReyunFailDao;
import com.linekong.report.reyun.dao.model.LogReportReyunDao;
import com.linekong.report.reyun.pojo.VO.LogReportReyunFailVO;
import com.linekong.report.reyun.pojo.VO.LogReportReyunVO;
import com.linekong.report.reyun.util.Common;
import com.linekong.report.reyun.util.Constant;
import com.linekong.report.reyun.util.ThreadPoolUtil;
import com.linekong.report.reyun.util.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FailReportReyunTimer {

    @Autowired
    public LogReportReyunFailDao logReportReyunFailDao;
    @Autowired
    public LogReportReyunDao logReportReyunDao;

    /**
     * 定时扫描失败发送记录表，并重新发送
     */
    public void failNoticeReyunTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                //查询失败表
                List<LogReportReyunFailVO> failReportList = logReportReyunFailDao.selectReportFailInfo();
                LogReportReyunFailVO logReportReyunFailVO = new LogReportReyunFailVO();
                //每500条执行一个循环然后睡2s
                for(int i = 0;i< failReportList.size();i++){
                    logReportReyunFailVO = failReportList.get(i);
                    //检查该记录是否已经成功通知过
                    int reportCount = logReportReyunDao.selectCountLogByState(logReportReyunFailVO.getReportMark(),logReportReyunFailVO.getReportFailType(),Constant.REPORT_SUCCESS+"");
                    if(reportCount > 0){
                        //删除记录
                        logReportReyunFailDao.deleteLogReportReyunFail(logReportReyunFailVO.getReportMark(), logReportReyunFailVO.getReportFailType());
                    }else {
                        //生成请求URL和参数
                        String url = Common.getNoticURL(logReportReyunFailVO.getReportFailType());
                        LogReportReyunVO logReportReyunVO = new LogReportReyunVO(logReportReyunFailVO.getReportMark(), logReportReyunFailVO.getReportFailType(),logReportReyunFailVO.getRequestIp(),
                                logReportReyunFailVO.getGameId(), Constant.NOT_REPORT);
                        //线程重新推送
                        ThreadPoolUtil.pool.submit(new ReportReyunChargeThread(url, logReportReyunFailVO.getReportFailParam(), logReportReyunVO, logReportReyunFailVO,
                                logReportReyunDao, logReportReyunFailDao));
                    }
                    if(i%500 == 0){
                        try {
                            Thread.sleep(2000);
                        }catch(Exception e){
                            LoggerUtil.error(FailReportReyunTimer.class,"Fail report Reyun Timer sleep error:"+e.toString());
                        }
                    }
                }




            }
        }, 1000*60*60*1, 1000*60*60*6);
    }
}
