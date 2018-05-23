package com.linekong.game.activity.pojo.VO;

import java.util.Date;

public class BindedPhoneVO {

    private String name;
    private String bindedPhoneNum;
    private long roleId;
    private String loginTime;


    public BindedPhoneVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBindedPhoneNum() {
        return bindedPhoneNum;
    }

    public void setBindedPhoneNum(String bindedPhoneNum) {
        this.bindedPhoneNum = bindedPhoneNum;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        /*SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.loginTime = sf.format(loginTime);*/
        this.loginTime = String.valueOf(loginTime.getTime() / 1000L);
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("name=").append(name);
        sb.append(",bindedPhoneNum=").append(bindedPhoneNum);
        sb.append(",loginTime=").append(loginTime);
        sb.append(",roleId=").append(roleId);
        return sb.toString();
    }
}
