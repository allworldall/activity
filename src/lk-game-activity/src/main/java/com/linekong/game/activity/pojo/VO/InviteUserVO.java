package com.linekong.game.activity.pojo.VO;

import com.linekong.game.activity.utils.SysCodeConstant;

/**
 * 发送邀请后（发送邀请的行为由客户端自己搞定，我们只记录邀请关系），接口返回的参数
 */
public class InviteUserVO {

    private int result;
    private String message;

    public InviteUserVO() {
    }

    public InviteUserVO(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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
        return sb.toString();
    }


    public InviteUserVO createResBean() {
        switch(this.result) {
            case SysCodeConstant.DEAL_SUCCESS : this.message = "success"; break;
            case SysCodeConstant.ERROR_SIGN:    this.message = "sign error"; break;
            case SysCodeConstant.ERROR_SYSTEM : this.message = "system error"; break;
            case SysCodeConstant.ERROR_PARAM  : this.message = "error param format"; break;
            case SysCodeConstant.ERROR_INVITED_REPEAT : this.message = "user has invited"; break;
            default: this.message = "other error";
        }
        return this;
    }
}
