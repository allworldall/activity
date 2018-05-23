package com.linekong.game.activity.dao.model;

import com.linekong.game.activity.pojo.PO.ItemInfoPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QueryPropertyDao {
    /**
     * 查询配置的参数值
     * @param configKey
     * @return
     */
    String getConfigValue(String configKey);

    /**
     * 根据活动key去配置表查活动Id并与物品表进行内连接，查询出物品信息
     * @param key
     * @return
     */
    List<ItemInfoPO> getItemInfo(String key);
}
