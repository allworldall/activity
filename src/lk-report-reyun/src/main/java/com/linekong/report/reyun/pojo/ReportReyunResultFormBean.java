package com.linekong.report.reyun.pojo;

import com.linekong.report.reyun.util.JsonUtil;

public class ReportReyunResultFormBean {

    public int status;   //返回状态 0：成功  -1：失败  错误415：请检查头信息中是否有Content-Type=application/json参数

    public Object result;   //错误信息

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ReportReyunResultFormBean(String noticResult) {
        ReportReyunResultFormBean bean = JsonUtil.convertJsonToBean(noticResult,ReportReyunResultFormBean.class);
        this.setResult(bean.getResult());
        this.setStatus(bean.getStatus());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NoticReyunResultFormBean{");
        sb.append("status='").append(status).append('\'');
        sb.append(", result='").append(result).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
