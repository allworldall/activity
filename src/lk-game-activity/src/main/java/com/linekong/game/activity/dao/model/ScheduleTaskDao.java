package com.linekong.game.activity.dao.model;

import com.linekong.game.activity.pojo.PO.ActivitySendItemInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 所有定时任务的Dao层，都写在此类中
 */
@Mapper
public interface ScheduleTaskDao {

    /**
     * 查询发送物品失败的记录，且小于最大发送次数
     * @param maxCount  最大发送次数
     * @return
     */
    List<ActivitySendItemInfoPO> getSendFailInfo(int maxCount);

    /**
     * 更新发送状态
     * @param logId
     * @param invokeResult
     */
    void updateSendItemStatus(@Param("logId") int logId, @Param("invokeResult") int invokeResult);
}
