package com.linekong.report.reyun.dao.model;

import com.linekong.report.reyun.pojo.VO.LogReportReyunVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LogReportReyunDao {
    /**
     * 插入信息
     * @param logReportReyunVO
     * @return
     */
    public int insertLogReportReyun(LogReportReyunVO logReportReyunVO);

    /**
     * 根据唯一标识和类别查询count
     * @param reportMark
     * @param reportType
     * @return
     */
    public int selectCountLog(@Param("reportMark")String reportMark, @Param("reportType")String reportType);

    /**
     * 根据唯一标识\类别\状态查询count
     * @param reportMark
     * @param reportType
     * @return
     */
    public int selectCountLogByState(@Param("reportMark")String reportMark, @Param("reportType")String reportType, @Param("reportState")String reportState);

    /**
     * 更新上报信息状态
     * @param logReportReyunVO
     * @returnState
     */
    public int updateLogReportReyunState(LogReportReyunVO logReportReyunVO);
}
