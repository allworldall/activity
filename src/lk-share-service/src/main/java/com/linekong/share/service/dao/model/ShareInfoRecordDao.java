package com.linekong.share.service.dao.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.linekong.share.service.pojo.vo.ActivitySendItemInfoDO;
import com.linekong.share.service.pojo.vo.ShareInfoRecordDO;
import com.linekong.share.service.pojo.vo.ShareUserInfoDO;
import com.linekong.share.service.utils.exception.DBException;
@Mapper
public interface ShareInfoRecordDao {
	
	/**
	 * 记录分享用户信息
	 * @param ShareInfoRecordDO shareInfoRecordDO
	 */
	public int insertShareUserInfo(ShareInfoRecordDO shareInfoRecordDO) throws DBException;
	/**
	 * 记录玩家分享信息被点击次数
	 * @param ShareInfoRecordDO shareInfoRecordDO
	 */
	public int insertClickShareInfo(ShareInfoRecordDO shareInfoRecordDO) throws DBException;
	/**
	 * 记录满足条件的用户
	 * @Param String uniqulyId
	 *               唯一标识
	 * @Param int gameId
	 *               游戏ID
	 * @Param int num
	 *               分享次数
	 */
	public int insertMetConditionUser(ShareUserInfoDO shareUserInfoDO) throws DBException;
	/**
	 * 记录赠送记录
	 */
	public int insertSendItemLog(ActivitySendItemInfoDO sendItemInfo) throws DBException;
	/**
	 * 更新物品发送状态
	 */
	public int updateSendItemStatus(@Param("logId") int logId,@Param("itemCode")String itemCode,@Param("result") int result) throws DBException;

}
