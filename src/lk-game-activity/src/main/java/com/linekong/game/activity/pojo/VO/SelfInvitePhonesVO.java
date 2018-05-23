package com.linekong.game.activity.pojo.VO;

import java.util.Arrays;

/**
 * 用于返回属于当前用户邀请的手机号
 */
public class SelfInvitePhonesVO {

    private int result;
    private String[] phoneNums;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String[] getPhoneNums() {
        return phoneNums;
    }

    public void setPhoneNums(String[] phoneNums) {
        this.phoneNums = phoneNums;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("result=").append(result);
        sb.append(",phoneNums=").append(Arrays.toString(phoneNums));
        return sb.toString();
    }
}
