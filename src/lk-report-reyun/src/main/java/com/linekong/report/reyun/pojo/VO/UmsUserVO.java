package com.linekong.report.reyun.pojo.VO;

public class UmsUserVO {

    public String userId;       //用户Id

    public String userName;     //用户名

    public String adId;         //广告ID

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UmsUserVO{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", adId='").append(adId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
