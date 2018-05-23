package com.linekong.game.activity.pojo.VO;

import com.linekong.game.activity.utils.SysCodeConstant;

import java.util.List;

public class BindedPhoneVOList {
    private int result;

    private String message;            //失败描述

    private List<BindedPhoneVO> data;  //存放已绑定手机号

    public BindedPhoneVOList() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<BindedPhoneVO> getData() {
        return data;
    }

    public void setData(List<BindedPhoneVO> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("result=").append(result);
        sb.append(",message=").append(message);
        sb.append(",data=").append(data);
        return sb.toString();
    }

    public static void createResBean(int result, BindedPhoneVOList bpList) {
        switch(result){
            case SysCodeConstant.DEAL_SUCCESS : bpList.setResult(SysCodeConstant.DEAL_SUCCESS);bpList.setMessage("success");  break;
            case SysCodeConstant.ERROR_SIGN:    bpList.setResult(SysCodeConstant.ERROR_SIGN);bpList.setMessage("sign error");break;
            case SysCodeConstant.ERROR_SYSTEM : bpList.setResult(SysCodeConstant.ERROR_SYSTEM);bpList.setMessage("system error");break;
            case SysCodeConstant.ERROR_PARAM  : bpList.setResult(SysCodeConstant.ERROR_PARAM);bpList.setMessage("request param format error");break;
            default: bpList.setResult(SysCodeConstant.ERROR_OTHER);bpList.setMessage("other error");break;
        }
    }
}
