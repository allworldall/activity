package com.linekong.report.reyun.pojo.VO;

public class LogReportReyunVO {

    public Long reportId;           //日志ID

    public String reportMark;       //发送请求唯一标识,  充值:charge_detail_id

    public String reportType;       //类别 ：1  充值

    public String reportDate;       //发送日期

    public String requestIp;        //请求iP

    public String gameId;          //游戏ID

    public Integer reportState;     //发送状态 0：未完成    -1：失败，会记录到失败表中   1：成功

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getReportMark() {
        return reportMark;
    }

    public void setReportMark(String reportMark) {
        this.reportMark = reportMark;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
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

    public Integer getReportState() {
        return reportState;
    }

    public void setReportState(Integer reportState) {
        this.reportState = reportState;
    }

    public LogReportReyunVO(String reportMark, String reportType, String requestIp, String gameId, Integer reportState) {
        this.reportMark = reportMark;
        this.reportType = reportType;
        this.requestIp = requestIp;
        this.gameId = gameId;
        this.reportState = reportState;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LogReportReyunVO{");
        sb.append("reportId='").append(reportId).append('\'');
        sb.append(", reportMark='").append(reportMark).append('\'');
        sb.append(", reportType='").append(reportType).append('\'');
        sb.append(", reportDate='").append(reportDate).append('\'');
        sb.append(", requestIp='").append(requestIp).append('\'');
        sb.append(", gameId=").append(gameId);
        sb.append(", reportState=").append(reportState);
        sb.append('}');
        return sb.toString();
    }
}
