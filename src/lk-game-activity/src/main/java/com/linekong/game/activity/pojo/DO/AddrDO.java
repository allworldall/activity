package com.linekong.game.activity.pojo.DO;

import com.linekong.game.activity.utils.annotation.DataValidate;
import com.linekong.game.activity.utils.annotation.RegexType;

/**
 * 客户端发送手机通讯录的实体类
 */
public class AddrDO {
    @DataValidate(description = "姓名", nullable = true, maxLength = 50)
    private String name;
    @DataValidate(description = "手机号", nullable = true)
    private String phoneNum;

    public AddrDO() {
    }

    public AddrDO(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        sb.append("name=").append(name);
        sb.append(",phoneNum=").append(phoneNum);
        return sb.toString();
    }
}
