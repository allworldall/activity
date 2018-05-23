package com.linekong.game.activity.dao.cache.impl;

import com.linekong.game.activity.dao.cache.GuavaCacheDao;
import com.linekong.game.activity.dao.model.QueryPropertyDao;
import com.linekong.game.activity.pojo.PO.ItemInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("GuavaCacheDaoImpl")
public class GuavaCacheDaoImpl implements GuavaCacheDao {

    @Autowired
    QueryPropertyDao queryPropertyDao;

    /**
     * 查询短信验证码服务商的url
     * @param configKey
     * @return
     */
    @Override
    public String getConfigValue(String configKey) {
        return queryPropertyDao.getConfigValue(configKey);
    }

    /**
     * 根据活动key去配置表查活动Id并与物品表进行内连接，查询出物品信息
     * @param key
     * @return
     */
    @Override
    public List<ItemInfoPO> getItemInfo(String key) {
        return queryPropertyDao.getItemInfo(key);
    }
}
