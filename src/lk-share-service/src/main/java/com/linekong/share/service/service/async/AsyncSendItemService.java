package com.linekong.share.service.service.async;

import java.util.List;
import java.util.Vector;

import com.linekong.share.service.dao.model.QueryConfigInfoDao;
import com.linekong.share.service.dao.model.ShareInfoRecordDao;
import com.linekong.share.service.pojo.vo.ActivityItemInfoDO;
import com.linekong.share.service.pojo.vo.ActivitySendItemInfoDO;
import com.linekong.share.service.pojo.vo.ShareUserInfoDO;
import com.linekong.share.service.utils.MD5Utils;
import com.linekong.share.service.utils.SysCodeConstant;
import com.linekong.share.service.utils.cache.CommonCache;
import com.linekong.share.service.utils.exception.DBException;
import com.linekong.share.service.utils.log.LoggerUtil;
import com.linekong.share.service.utils.xmlrpc.InvokeXmlRpcUtil;

public class AsyncSendItemService implements Runnable{
	
	private ShareInfoRecordDao shareInfoRecordDao;
	
	private QueryConfigInfoDao queryConfigInfoDao;
	
	private ShareUserInfoDO shareUserInfoDO;
	
	public AsyncSendItemService(ShareInfoRecordDao shareInfoRecordDao,
			QueryConfigInfoDao queryConfigInfoDao,ShareUserInfoDO shareUserInfoDO) {
		this.shareInfoRecordDao = shareInfoRecordDao;
		this.queryConfigInfoDao = queryConfigInfoDao;
		this.shareUserInfoDO = shareUserInfoDO;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		//获取到活动号对应的物品信息
		try {
			List<ActivityItemInfoDO> list = queryConfigInfoDao.queryActivityItemInfo(shareUserInfoDO.getActivityId());
			for (int i = 0 ; i< list.size();i++) { //循环物品信息
				ActivityItemInfoDO  item = list.get(i);
				//记录信息
				ActivitySendItemInfoDO sendItem = new ActivitySendItemInfoDO();
				sendItem.setActivityId(shareUserInfoDO.getActivityId());
				sendItem.setGameId(shareUserInfoDO.getGameId());
				sendItem.setGatewayId(shareUserInfoDO.getGatewayId());
				sendItem.setLogId(shareUserInfoDO.getLogId());
				sendItem.setPassportName(shareUserInfoDO.getPassportName());
				sendItem.setItemCode(item.getItemCode());
				sendItem.setItemNum(item.getItemNum());
				sendItem.setRoleId(shareUserInfoDO.getRoleId());
				int result = shareInfoRecordDao.insertSendItemLog(sendItem);
				if(result > 0) {//记录成功执行赠送
					Vector v = new Vector();
					v.add(sendItem.getPassportName());
					v.add(sendItem.getItemCode());
					v.add(sendItem.getItemNum());
					v.add(sendItem.getGameId());
					v.add(1); //正式区
					v.add(sendItem.getGatewayId());
					v.add(sendItem.getActivityId()+"");
					v.add(MD5Utils.getMd5(sendItem.getPassportName()+CommonCache.getConfigInfo(SysCodeConstant.INVOKE_XML_RPC_KEY)));//生成MD5值
					v.add(sendItem.getRoleId()+"");
					int invokeResult = InvokeXmlRpcUtil.getInstance(sendItem.getGameId()).invokeInt("gmXmlRpcService.itemAdd_Activity_new", v);
					shareInfoRecordDao.updateSendItemStatus(sendItem.getLogId(), sendItem.getItemCode(), invokeResult);
				}
			}
		} catch (DBException e) {
			LoggerUtil.error(AsyncSendItemService.class, " async execute db error:"+e.toString());
		}
		
	}

}
