package com.linekong.game.activity.utils.schedule;

import com.linekong.game.activity.dao.model.ScheduleTaskDao;
import com.linekong.game.activity.pojo.PO.ActivitySendItemInfoPO;
import com.linekong.game.activity.utils.DataFormatUtils.StringUtils;
import com.linekong.game.activity.utils.SysCodeConstant;
import com.linekong.game.activity.utils.cache.GuavaUtil;
import com.linekong.game.activity.utils.log.LoggerUtil;
import com.linekong.game.activity.utils.sign.MD5Util;
import com.linekong.game.activity.utils.xmlrpc.InvokeXmlRpcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Vector;

/**
 * 此类用于定时读取发送物品表，如有发送失败的，就进行补发
 */
@Component
public class SendItemFail {

    @Autowired
    ScheduleTaskDao scheduleTaskDao;

    @Scheduled(cron="0 0 1/1 * * ?")  //每一小时重发一次
    public void doTask() {
        LoggerUtil.info(this.getClass(), "SendItemFail schedule task start");
        try {
            String maxSendCount = GuavaUtil.getConfigValue(SysCodeConstant.MAX_SEND_COUNT);
            if (StringUtils.isEmpty(maxSendCount)) {
                LoggerUtil.error(this.getClass(), "config error, error info:max.send.count not config");
                return;
            }
            //调用xmlRpc时，数据加密的key
            String xmlKey = GuavaUtil.getConfigValue(SysCodeConstant.INVOKE_XML_RPC_KEY);
            if(StringUtils.isEmpty(xmlKey)){
                LoggerUtil.error(this.getClass(), "config error, error info:xml_rpc_key not config");
                return;
            }
            List<ActivitySendItemInfoPO> sendItemList = scheduleTaskDao.getSendFailInfo(Integer.parseInt(maxSendCount));
            if(sendItemList == null || sendItemList.size()==0){
                return ;
            }
            for (ActivitySendItemInfoPO sendInfo: sendItemList) {
                Vector v = new Vector();
                v.add(sendInfo.getPassportName());
                v.add(sendInfo.getItemCode());
                v.add(sendInfo.getItemNum());
                v.add(sendInfo.getGameId());
                v.add(1); //正式区
                v.add(sendInfo.getGatewayId());
                v.add(sendInfo.getActivityId()+"");
                v.add(MD5Util.getMD5(sendInfo.getPassportName()+xmlKey));//生成MD5值
                v.add(sendInfo.getRoleId()+"");
                LoggerUtil.info(this.getClass(), "send item param:"+v.toString());
                int invokeResult = InvokeXmlRpcUtil.getInstance().invokeInt("gmXmlRpcService.itemAdd_Activity_new", v);
                //更改发送记录表的发送状态
                scheduleTaskDao.updateSendItemStatus(sendInfo.getLogId(), invokeResult);
            }
        }catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.error(this.getClass(), "schedule task error,error info:" + e.toString());
        }
    }
}
