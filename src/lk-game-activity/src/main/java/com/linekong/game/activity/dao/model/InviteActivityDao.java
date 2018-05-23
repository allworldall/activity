package com.linekong.game.activity.dao.model;

import com.linekong.game.activity.pojo.DO.*;
import com.linekong.game.activity.pojo.VO.BindedPhoneVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface InviteActivityDao {

    /**
     * 插入或更新登录用户信息
     * @param loginInfoDO
     */
    void recordLoginInfo(LoginInfoDO loginInfoDO);

    /**
     * 根据账号信息，查询出该账号绑定过的手机号
     */
    List<String> getPhoneNum(LoginInfoDO loginInfoDO);

    /**
     *获取已绑定手机号用户的最近登录时间
     * @param addrDO
     * @param <BindedPhoneVO>
     * @return
     */
    com.linekong.game.activity.pojo.VO.BindedPhoneVO getBindedPhoneVO(AddrDO addrDO);

    /**
     *记录上报通讯录
     * @param addrListDO
     */
    void recordReportData(AddrDOList addrListDO);

    /**
     * 查询通讯录中有没有已绑定的手机号
     * @param phoneNums  手机号
     * @param appId      应用id
     * @return
     */
    Set<String> queryBindedPhone(@Param("phoneNums") Set<String> phoneNums, @Param("appId") int appId);

    /**
     * 查询邀请关系表有无数据
     * @param appId
     * @param invitedNum
     * @return
     */
    int countInvite(@Param("appId") int appId, @Param("invitedNum") String invitedNum);

    /**
     * 记录邀请关系
     * @param inviteInfoDO
     */
    void insertInviteRelation(InviteInfoDO inviteInfoDO);

    /**
     * 查询当前用户邀请过的用户
     * @param currenUser     当前用户对象
     * @return
     */
    List<String> getSelfInvitePhones(CurrenUserDO currenUser);
}
