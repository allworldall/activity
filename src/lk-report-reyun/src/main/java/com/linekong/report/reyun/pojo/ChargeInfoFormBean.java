package com.linekong.report.reyun.pojo;

import com.linekong.report.reyun.util.annotation.DataValidate;

/**
 * 充值信息
 */
public class ChargeInfoFormBean {

    public String userId;           //用户ID

    public String userName;         //用户名
    @DataValidate(description = "游戏ID", nullable = false)
    public String gameId;           //游戏ID
    @DataValidate(description = "蓝港订单号", nullable = false)
    public String chargeDetailId;  //蓝港订单号
    @DataValidate(description = "货币类型", nullable = false)
    public String currenyType;      //货币类型，按照国际标准组织ISO 4217中规范的3位字母，例如CNY人民币、USD美金等
    @DataValidate(description = "充值金额", nullable = false)
    public String chargeMoney;      //充值金额
    @DataValidate(description = "支付类型", nullable = false)
    public String paymentType;      //支付类型

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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getChargeDetailId() {
        return chargeDetailId;
    }

    public void setChargeDetailId(String chargeDetailId) {
        this.chargeDetailId = chargeDetailId;
    }

    public String getCurrenyType() {
        return currenyType;
    }

    public void setCurrenyType(String currenyType) {
        this.currenyType = currenyType;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChargeInfoFormBean{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", gameId='").append(gameId).append('\'');
        sb.append(", chargeDetailId='").append(chargeDetailId).append('\'');
        sb.append(", currenyType='").append(currenyType).append('\'');
        sb.append(", chargeMoney='").append(chargeMoney).append('\'');
        sb.append(", paymentType='").append(paymentType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
