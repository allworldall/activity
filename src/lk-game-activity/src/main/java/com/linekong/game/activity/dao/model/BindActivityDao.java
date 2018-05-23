package com.linekong.game.activity.dao.model;

import com.linekong.game.activity.pojo.PO.AccountInfoPO;
import com.linekong.game.activity.pojo.PO.ActRewardInfoPO;
import com.linekong.game.activity.pojo.PO.ActivitySendItemInfoPO;
import com.linekong.game.activity.pojo.PO.PhoneInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BindActivityDao {

    /**
     * 同一款游戏同一个用户只能绑定一次
     * @param gameId
     * @param phoneNum
     * @return
     */
    int countBind(@Param("appId") int appId, @Param("phoneNum") String phoneNum);

    //保存到记录验证码的日志表里
    void insertLogTable(@Param("phoneNum") String phoneNum, @Param("phoneCode") String phoneCode);

    //保存到验证码信息表里（有验证码状态字段）
    void insertPhoneCode(@Param("phoneNum") String phoneNum,@Param("phoneCode")  String phoneCode);

    /**
     * 查询库里是否有匹配的验证码，
     * @param phoneCode  验证码
     * @param gameId     游戏id
     * @param phoneNum   手机号
     * @return
     */
    int countMatchCode(@Param("phoneCode") String phoneCode, @Param("phoneNum") String phoneNum);

    /**
     * 获取sys_phone_info表的该条记录的主键
     * @param phoneNum
     * @return
     */
    PhoneInfoPO getPhoneInfo(String phoneNum);

    /**
     * 插入一条手机号记录，并返回主键
     * @param phoneInfoPO
     * @return
     */
    void insertPhoneInfo(PhoneInfoPO phoneInfoPO);

    /**
     * 插入账号信息表，返回主键
     * @param accountInfoPO
     * @return
     */
    void insertAccountInfo(AccountInfoPO accountInfoPO);

    /**
     * 插入账号和手机号绑定关联表
     * @param phone_id
     * @param passportId
     */
    void insertBindInfo(@Param("phoneId")int phoneId, @Param("passportId")int passportId, @Param("appId") int appId);

    /**
     * 删除验证码
     * @param phoneNum
     * @param phoneCode
     */
    void deletePhoneCode(String phoneNum);

    //更新账号表的绑定状态
    void updateBindStatus(AccountInfoPO accountInfoPO);

    /**
     * 获取赠送活动实体类，查询邀请关系表
     * @param phoneNum
     * @param appId
     * @return
     */
    ActRewardInfoPO getGiveInfo(@Param("phoneNum") String phoneNum, @Param("appId") int appId);

    /**
     * 发送奖励前先记录数据库,返回主键
     * @param actendItemInfoDO
     */
    int insertSendItemLog(ActivitySendItemInfoPO actendItemInfoDO);

    /**
     * 更改发送记录表的发送状态
     * @param logId
     * @param invokeResult
     */
    void updateSendItemStatus(@Param("logId") int logId, @Param("invokeResult") int invokeResult);

    /**
     * 查询该账号是否存在
     * @param accountInfoPO
     * @return
     */
    AccountInfoPO getAccountInfoPO(AccountInfoPO accountInfoPO);

    /**
     * 将该用户的另一个账号也绑定手机号
     * @param passportId
     * @param phoneNum
     * @param appId
     */
    void insertBindInfoElse(@Param("passportId") int passportId, @Param("phoneNum") String phoneNum, @Param("appId") String appId);
}
