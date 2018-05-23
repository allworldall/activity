package com.linekong.report.reyun.pojo.VO;

import com.linekong.report.reyun.util.Constant;

public class LogReportReyunFailVO {

    public Long reportailId;            //主键：发送失败Id

    public String reportMark;           //消息标记 充值：charge_detail_id

    public String reportFailReason;     //失败原因

    public String reportFailType;        //类别 ：1  充值

    public String reportFailParam;       //发送参数

    public String reportFailResult;      //发送结果

    public Integer reportFailCount;      //发送次数

    public String reportFailDate;        //发送日期

    public String requestIp;              //请求IP

    public String gameId;                 //游戏ID

    public Long getReportailId() {
        return reportailId;
    }

    public void setReportailId(Long reportailId) {
        this.reportailId = reportailId;
    }

    public String getReportMark() {
        return reportMark;
    }

    public void setReportMark(String reportMark) {
        this.reportMark = reportMark;
    }

    public String getReportFailReason() {
        return reportFailReason;
    }

    public void setReportFailReason(String reportFailReason) {
        this.reportFailReason = reportFailReason;
    }

    public String getReportFailType() {
        return reportFailType;
    }

    public void setReportFailType(String reportFailType) {
        this.reportFailType = reportFailType;
    }

    public String getReportFailParam() {
        return reportFailParam;
    }

    public void setReportFailParam(String reportFailParam) {
        this.reportFailParam = reportFailParam;
    }

    public String getReportFailResult() {
        return reportFailResult;
    }

    public void setReportFailResult(String reportFailResult) {
        this.reportFailResult = reportFailResult;
    }

    public Integer getReportFailCount() {
        return reportFailCount;
    }

    public void setReportFailCount(Integer reportFailCount) {
        this.reportFailCount = reportFailCount;
    }

    public String getReportFailDate() {
        return reportFailDate;
    }

    public void setReportFailDate(String reportFailDate) {
        this.reportFailDate = reportFailDate;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public LogReportReyunFailVO() {
    }

    public void setReportFailInfo(String reason,String result) {
        this.reportFailReason = reason;
        this.reportFailResult = result;
    }

    public LogReportReyunFailVO(String reportMark,  String reportFailType, String reportFailParam,
                                String requestIp, String gameId) {
        this.reportMark = reportMark;
        this.reportFailReason = "init reason";
        this.reportFailType = reportFailType;
        this.reportFailParam = reportFailParam;
        this.reportFailResult =  Constant.INIT_RESULT+"";
        this.reportFailCount = 1;
        this.requestIp = requestIp;
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LogReportReyunFailVO{");
        sb.append("reportailId=").append(reportailId);
        sb.append(", reportMark='").append(reportMark).append('\'');
        sb.append(", reportFailReason='").append(reportFailReason).append('\'');
        sb.append(", reportFailType='").append(reportFailType).append('\'');
        sb.append(", reportFailParam='").append(reportFailParam).append('\'');
        sb.append(", reportFailResult='").append(reportFailResult).append('\'');
        sb.append(", reportFailCount=").append(reportFailCount);
        sb.append(", reportFailDate='").append(reportFailDate).append('\'');
        sb.append(", requestIp='").append(requestIp).append('\'');
        sb.append(", gameId=").append(gameId);
        sb.append('}');
        return sb.toString();
    }
}
