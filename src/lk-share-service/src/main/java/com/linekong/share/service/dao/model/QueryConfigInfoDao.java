package com.linekong.share.service.dao.model;

import java.util.List;

import com.linekong.share.service.pojo.form.AwardWinnerForm;
import com.linekong.share.service.pojo.form.RecordUserShareInfoForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.linekong.share.service.pojo.vo.ActivityItemInfoDO;
import com.linekong.share.service.pojo.vo.ShareUserInfoDO;
import com.linekong.share.service.utils.exception.DBException;
@Mapper
public interface QueryConfigInfoDao {
	
	/**
	 * 通过属性值来查询对应的key值
	 * @param String key
	 * @return String
	 */
	public String queryConfigInfo(@Param("key")String key) throws DBException;
	/**
	 * 查询分享id对应的唯一值
	 * @param String uniqulyId
	 *               唯一标识值
	 * @return ShareUserInfoDO
	 */
	public ShareUserInfoDO queryShareUserInfo(@Param("uniqulyId")String uniqulyId) throws DBException;
	/**
	 * 查询用户是否满足条件
	 */
	public int queryShareClickCondition(@Param("uniqulyId")String uniqulyId) throws DBException;
	
	/**
	 * 通过活动ID查询需要赠送的物品信息
	 * @param int activityId
	 * @return List<ActivityItemInfoDO>
	 */
	public List<ActivityItemInfoDO> queryActivityItemInfo(@Param("activityId") int activityId) throws DBException;

	/**
	 * 查询该用户的累计奖励次数
	 * @param form  获奖用户对象
	 * @return
	 */
	public int querySumCount(AwardWinnerForm form);

	/**
	 * 查询该用户当日累计奖励次数
	 * @param form  获奖用户对象
	 * @return
	 */
	public int queryDailyCount(AwardWinnerForm form);

	/**
	 * 通过账号查询唯一标识
	 * @param form
	 * @return
	 */
    public String queryUniqulyIdByAccount(RecordUserShareInfoForm form);
}
