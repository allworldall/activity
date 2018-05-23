package com.linekong.report.reyun.dao.model;

import com.linekong.report.reyun.pojo.VO.LogReportReyunFailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogReportReyunFailDao {

    /**
     * 插入信息
     * @param logReportReyunFailVO
     * @return
     */
    public int insertLogReportReyunFail(LogReportReyunFailVO logReportReyunFailVO);

    /**
     * 查询通知次数小于3次的信息
     * @return
     */
    public List<LogReportReyunFailVO> selectReportFailInfo();

    /**
     * 更新发送次数
     * @param logReportReyunFailVO
     * @return
     */
    public int updateLogReportReyunFail(LogReportReyunFailVO logReportReyunFailVO);

    /**
     * 根据消息标记和类别查询失败订单Count
     * @param reportMark
     * @param reportFailType
     * @return
     */
    public int selectCountFailInfo(@Param("reportMark")String reportMark ,@Param("reportFailType")String reportFailType);

    /**
     * 删除发送失败记录
     * @param reportMark
     * @param reportFailType
     * @return
     */
    public int deleteLogReportReyunFail(@Param("reportMark")String reportMark ,@Param("reportFailType")String reportFailType);
}
