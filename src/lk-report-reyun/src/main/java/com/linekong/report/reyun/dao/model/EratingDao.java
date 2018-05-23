package com.linekong.report.reyun.dao.model;

import com.linekong.report.reyun.pojo.VO.UmsUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EratingDao {
    /**
     * 获取设备ID根据用户名
     * @return
     */
    public UmsUserVO getEquipmentIdByName(@Param("userName") String userName , @Param("tableName") String tableName);

    /**
     * 获取设备ID根据用户ID
     * @return
     */
    public UmsUserVO getEquipmentIdById(@Param("userId") String userId ,@Param("tableName") String tableName);

}
