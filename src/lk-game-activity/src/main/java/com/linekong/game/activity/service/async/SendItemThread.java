package com.linekong.game.activity.service.async;

import com.linekong.game.activity.dao.model.BindActivityDao;
import com.linekong.game.activity.pojo.DO.CheckPhoneCodeDO;
import com.linekong.game.activity.pojo.PO.ActRewardInfoPO;
import com.linekong.game.activity.pojo.PO.ActivitySendItemInfoPO;
import com.linekong.game.activity.pojo.PO.ItemInfoPO;
import com.linekong.game.activity.utils.DataFormatUtils.StringUtils;
import com.linekong.game.activity.utils.SysCodeConstant;
import com.linekong.game.activity.utils.cache.GuavaUtil;
import com.linekong.game.activity.utils.log.LoggerUtil;
import com.linekong.game.activity.utils.sign.MD5Util;
import com.linekong.game.activity.utils.xmlrpc.InvokeXmlRpcUtil;

import java.util.List;
import java.util.Vector;

public class SendItemThread implements Runnable {

    private int appId;            //应用Id
    private String phoneNum;      //绑定手机号
    private BindActivityDao bindActivityDao;
    private CheckPhoneCodeDO checkPhoneCode;//校验验证码请求对象

    public SendItemThread(CheckPhoneCodeDO checkPhoneCode, String phoneNum, int appId, BindActivityDao bindActivityDao){
        this.appId = appId;
        this.phoneNum = phoneNum;
        this.bindActivityDao = bindActivityDao;
        this.checkPhoneCode = checkPhoneCode;
    }
    @Override
    public void run() {
        try {
            //获取到活动号及对应的物品信息，再封装
            //1、查询绑定者奖励活动号，如果未配置活动
            List<ItemInfoPO> actInfo = GuavaUtil.getItemInfo(SysCodeConstant.NNTG_ACT1_GIVE_ITEM);
            if (actInfo == null || actInfo.size() == 0) {
                LoggerUtil.info(this.getClass(), "no activity config ,param:activityType="+SysCodeConstant.NNTG_ACT1_GIVE_ITEM);
                return;
            }
            //2、调用xmlRpc时，数据加密的key
            String xmlKey = GuavaUtil.getConfigValue(SysCodeConstant.INVOKE_XML_RPC_KEY);
            if(StringUtils.isEmpty(xmlKey)){
                LoggerUtil.error(this.getClass(), "config error, error info:xml_rpc_key not config");
                return;
            }
            //3、给绑定者发奖励
            for(ItemInfoPO itemInfoPO : actInfo){
                ActivitySendItemInfoPO actsendItemInfoDO = new ActivitySendItemInfoPO(checkPhoneCode, itemInfoPO);
                sendItem(actsendItemInfoDO, xmlKey);
            }
            //4、查询邀请者奖励活动号，如果未配置活动
            List<ItemInfoPO> actInfoInviter = GuavaUtil.getItemInfo(SysCodeConstant.NNTG_ACT2_GIVE_ITEM);
            if (actInfoInviter == null || actInfoInviter.size() == 0) {
                LoggerUtil.info(this.getClass(), "no activity2 config ,param:activityType="+SysCodeConstant.NNTG_ACT2_GIVE_ITEM);
                return;
            }
            //5、获取邀请人信息，查询邀请关系表,如果该手机号未被邀请过，则直接返回
            ActRewardInfoPO actRewardInfoPO = bindActivityDao.getGiveInfo(phoneNum, appId);
            if(actRewardInfoPO == null || StringUtils.isEmpty(actRewardInfoPO.getPassportName()) || 0==actRewardInfoPO.getGameId()){
                LoggerUtil.info(this.getClass(),",currentThread="+Thread.currentThread().getName()+",no invite relation ,params:phoneNum="+phoneNum+",appId="+appId);
                return;
            }
            //6、给邀请者发奖励
            for(ItemInfoPO itemInfoPO : actInfoInviter){
                ActivitySendItemInfoPO actsendItemInfoDO = new ActivitySendItemInfoPO(actRewardInfoPO, itemInfoPO);
                sendItem(actsendItemInfoDO, xmlKey);
            }

        } catch (Exception e) {
            LoggerUtil.error(this.getClass(), "deal param appId=" + appId +
                    ",phoneNum"+phoneNum+" ,async execute error:"+e.toString());
        }
    }

    /**
     * 发送奖励
     */
    public void sendItem(ActivitySendItemInfoPO actsendItemInfoDO, String xmlKey) {
        //发送奖励前先记录数据库，插入后并返回主键
        int result = bindActivityDao.insertSendItemLog(actsendItemInfoDO);
        if(result >0 ) { //执行赠送
            Vector v = new Vector();
            v.add(actsendItemInfoDO.getPassportName());
            v.add(actsendItemInfoDO.getItemCode());
            v.add(actsendItemInfoDO.getItemNum());
            v.add(actsendItemInfoDO.getGameId());
            v.add(1); //正式区
            v.add(actsendItemInfoDO.getGatewayId());
            v.add(actsendItemInfoDO.getActivityId()+"");
            v.add(MD5Util.getMD5(actsendItemInfoDO.getPassportName()+xmlKey));//生成MD5值
            v.add(actsendItemInfoDO.getRoleId()+"");
            LoggerUtil.info(this.getClass(), "send item param:"+v.toString());
            int invokeResult = InvokeXmlRpcUtil.getInstance().invokeInt("gmXmlRpcService.itemAdd_Activity_new", v);
            //更改发送记录表的发送状态
            bindActivityDao.updateSendItemStatus(actsendItemInfoDO.getLogId(), invokeResult);
        }else {
            LoggerUtil.error(this.getClass(), "DB invoke insertSendItemLog error,param:"+actsendItemInfoDO);
            return;//不用break，可以将发送记录都进行保存
        }
    }
}
