package com.linekong.game.activity.dao.cache;

import com.linekong.game.activity.pojo.PO.ItemInfoPO;

import java.util.List;

public interface GuavaCacheDao {
    /**
     * 查询短信验证码服务商的url
     * @param key
     * @return
     */
    String getConfigValue(String key);

    /**
     * 根据活动key去配置表查活动Id并与物品表进行内连接，查询出物品信息
     * @param key
     * @return
     */
    List<ItemInfoPO> getItemInfo(String key);
}
