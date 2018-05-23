package com.linekong.game.activity.pojo.PO;

import com.linekong.game.activity.pojo.DO.CheckPhoneCodeDO;

/**
 * dao层需要的实体对象
 */
public class AccountInfoPO {
    private int passportId;   //对应账号表的主键
    private String account;   //账号
    private int accountType;  //账号类型
    private int bindStatus;   //绑定状态
    private int status;       //账号状态
    private int gameId;       //游戏id
    private long roleId;      //角色

    public AccountInfoPO() {
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(int bindStatus) {
        this.bindStatus = bindStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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
        sb.append("passportId=").append(passportId);
        sb.append(",account=").append(account);
        sb.append(",accountType=").append(accountType);
        sb.append(",bindStatus=").append(bindStatus);
        sb.append(",status=").append(status);
        sb.append(",gameId=").append(gameId);
        sb.append(",roleId=").append(roleId);
        return sb.toString();
    }

    /**
     * 创建此实体类对象，供dao层使用
     * @param checkPhoneCode
     * @return
     */
    public static AccountInfoPO createPO(CheckPhoneCodeDO checkPhoneCode) {
        AccountInfoPO po = new AccountInfoPO();
        po.setAccount(checkPhoneCode.getAccount());
        po.setAccountType(Integer.parseInt(checkPhoneCode.getAccountType()));
        po.setGameId(Integer.parseInt(checkPhoneCode.getGameId()));
        po.setRoleId(Long.parseLong(checkPhoneCode.getRoleId()));
        return po;
    }


    public static AccountInfoPO createPO(String account, String accountType, String gameId, String roleId) {
        AccountInfoPO po = new AccountInfoPO();
        po.setAccount(account);
        po.setAccountType(Integer.parseInt(accountType));
        po.setGameId(Integer.parseInt(gameId));
        po.setRoleId(Long.parseLong(roleId));
        return po;
    }
}
