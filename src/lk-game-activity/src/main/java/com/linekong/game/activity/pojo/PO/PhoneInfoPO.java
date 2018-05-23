package com.linekong.game.activity.pojo.PO;

/**
 * Dao层参数实体类
 */
public class PhoneInfoPO {
    private int phoneId;     //sys_phone_info表主键
    private String phoneNum; //手机号

    public PhoneInfoPO() {
    }

    public PhoneInfoPO(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("phoneId=").append(phoneId);
        sb.append(",phoneNum=").append(phoneNum);
        return sb.toString();
    }
}
