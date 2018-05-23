package com.linekong.share.service.pojo.vo;

public class RewardTimesVO {
    private int result;      //状态码

    private int dailyTimes;  //每日奖励次数

    private int sumTimes;    //累计次数

    public RewardTimesVO() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getDailyTimes() {
        return dailyTimes;
    }

    public void setDailyTimes(int dailyTimes) {
        this.dailyTimes = dailyTimes;
    }

    public int getSumTimes() {
        return sumTimes;
    }

    public void setSumTimes(int sumTimes) {
        this.sumTimes = sumTimes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("result=").append(result);
        sb.append(",dailyTimes=").append(dailyTimes);
        sb.append(",sumTimes=").append(sumTimes);
        return sb.toString();
    }
}
