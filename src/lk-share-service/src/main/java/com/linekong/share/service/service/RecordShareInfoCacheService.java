package com.linekong.share.service.service;

import com.linekong.share.service.pojo.form.AwardWinnerForm;
import com.linekong.share.service.pojo.vo.*;
import com.linekong.share.service.redis.RedisClientTemplate;
import com.linekong.share.service.utils.DataFormatUtils.StringUtils;
import com.linekong.share.service.utils.function.CreateShortUrlFunction;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linekong.share.service.dao.cache.RecordShareInfoCache;
import com.linekong.share.service.dao.model.QueryConfigInfoDao;
import com.linekong.share.service.dao.model.ShareInfoRecordDao;
import com.linekong.share.service.pojo.form.RecordUserShareInfoForm;
import com.linekong.share.service.pojo.form.ClickShareInfoRecordForm;
import com.linekong.share.service.service.async.AsyncSendItemService;
import com.linekong.share.service.utils.IPUtils;
import com.linekong.share.service.utils.MD5Utils;
import com.linekong.share.service.utils.SysCodeConstant;
import com.linekong.share.service.utils.cache.CommonCache;
import com.linekong.share.service.utils.exception.DBException;
import com.linekong.share.service.utils.exception.RedisException;
import com.linekong.share.service.utils.log.LoggerUtil;
import com.linekong.share.service.utils.thread.ThreadPoolUtil;

@Service
public class RecordShareInfoCacheService {
	@Autowired
	private RecordShareInfoCache recordShareInfoCache;
	@Autowired
	private ShareInfoRecordDao shareInfoRecordDao;
	@Autowired
	private QueryConfigInfoDao queryConfigInfoDao;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	/**
	 * 分享之前记录此用户的信息
	 * 
	 * @param RecordUserShareInfoForm
	 *            form
	 * @return int
	 */
	public int recordShareUserInfo(RecordUserShareInfoForm form, RecordShareInfoRespVO resp) {
		// 校验加密key值是否正确
		if (!MD5Utils.getMd5(form.getUniqulyId() + form.getPassportName() + form.getGameId() + form.getGatewayId())
				.equals(form.getSign())) {
			resp.createResp(SysCodeConstant.SIGN_ERROR, "");
			return SysCodeConstant.SIGN_ERROR;
		}
		try {
			if (recordShareInfoCache.existKey(SysCodeConstant.REDIS_KEY_SHARE_USER_LOG + form.getUniqulyId())) {
				resp.createResp(SysCodeConstant.SUCCESS, form.getUniqulyId());
				return SysCodeConstant.SUCCESS;
			} else {//唯一标识不存在，有可能客户端生成了一个新的，由可能是redis保存失败这种先查库
				try {
					ShareUserInfoDO info = queryConfigInfoDao.queryShareUserInfo(form.getUniqulyId());
					if (info == null) {
						//有可能是之前分享过的账号生成了一个其它的唯一标识，再通过账号查询唯一标识
						String uniqulyId = queryConfigInfoDao.queryUniqulyIdByAccount(form);
						if(StringUtils.isEmpty(uniqulyId)) {//说明试一次有效的全新的分享
							// 记录分享用户信息
							ShareInfoRecordDO shareInfoRecordDO = new ShareInfoRecordDO();
							shareInfoRecordDO.setUniqulyId(form.getUniqulyId());
							shareInfoRecordDO.setPassportName(form.getPassportName());
							shareInfoRecordDO.setGameId(Integer.parseInt(form.getGameId()));
							shareInfoRecordDO.setGatewayId(Integer.parseInt(form.getGatewayId()));
							shareInfoRecordDO.setActivityId(Integer.parseInt(form.getActivityId()));
							shareInfoRecordDO
									.setRoleId(Integer.parseInt(form.getRoleId() == null ? "0" : form.getRoleId()));
							shareInfoRecordDao.insertShareUserInfo(shareInfoRecordDO);
							// 数据记录到缓存中
							recordShareInfoCache.recordShareUserInfo(form);
							resp.createResp(SysCodeConstant.SUCCESS, form.getUniqulyId());
						}else {//说明该账号已经分享过了，将该账号的之前分享的唯一标识返给客户端
							resp.createResp(SysCodeConstant.SUCCESS, uniqulyId);
						}
						return SysCodeConstant.SUCCESS;
					} else { // 存在记录到缓存中
						recordShareInfoCache.recordShareUserInfo(form);
						resp.createResp(SysCodeConstant.SUCCESS, form.getUniqulyId());
						return SysCodeConstant.SUCCESS;
					}
				} catch (DBException e) {
					LoggerUtil.error(RecordShareInfoCacheService.class,
							"execute insertShareUserInfo error:" + e.toString());
					resp.createResp(SysCodeConstant.DB_ERROR, "");
					return SysCodeConstant.DB_ERROR;
				}

			}
		} catch (RedisException e) {
			LoggerUtil.error(RecordShareInfoCacheService.class, "redis error :" + e.toString());
			resp.createResp(SysCodeConstant.DB_ERROR, "");
			return SysCodeConstant.REDIS_ERROR;
		}
	}

	/**
	 * 记录用户分享链接被点击次数
	 */
	public int recordShareClickInfo(ClickShareInfoRecordForm form) {
		// 校验加密key值是否正确
		if (!MD5Utils.getMd5(form.getUniqulyId()).equals(form.getSign())) {
			return SysCodeConstant.SIGN_ERROR;
		}
		// 判断uniquylyId是否存在
		int status = validateUniqulyIdExsit(form.getUniqulyId());
		if (status != SysCodeConstant.SUCCESS) {
			return status;
		}
		try {
			// 存在执行以下操作
			int result = recordShareInfoCache.recordShareClickInfo(form);
			try {
				if (result == SysCodeConstant.SUCCESS) {// 将数据写入到数据库中
					//记录并赠送
					ShareInfoRecordDO shareInfoRecordDO = new ShareInfoRecordDO();
					shareInfoRecordDO.setUniqulyId(form.getUniqulyId());
					shareInfoRecordDO.setClickIp(form.getClickIp());
					shareInfoRecordDO.setClickIpNum(
							IPUtils.ipToLong(form.getClickIp() == null ? "127.0.0.1" : form.getClickIp()));
					shareInfoRecordDO.setClickUa(form.getClickUa());
					shareInfoRecordDO.setClickUrl(form.getClickUrl());
					//每次赠送之前，记录一次有效点击
					result = shareInfoRecordDao.insertClickShareInfo(shareInfoRecordDO);
					if (result > 0) {
						//进行物品赠送
						ShareUserInfoDO shareUserInfoDO = new ShareUserInfoDO();
						shareUserInfoDO = queryConfigInfoDao.queryShareUserInfo(form.getUniqulyId());
						shareUserInfoDO.setClickNum(1);
						int ret = shareInfoRecordDao.insertMetConditionUser(shareUserInfoDO);
						if (ret > 0) {// 进行数据写入
							// 进行物品赠送和数据写入
							ThreadPoolUtil.pool.execute(
									new AsyncSendItemService(shareInfoRecordDao, queryConfigInfoDao, shareUserInfoDO));
						}
						return SysCodeConstant.SUCCESS;
					} else {
						return SysCodeConstant.ALREADY_CLICK;
					}
				} else {
					return result;
				}
			} catch (Exception e) {
				LoggerUtil.error(RecordShareInfoCacheService.class,
						"execute recordShareClickInfo error:" + e.toString());
				return SysCodeConstant.DB_ERROR;
			}
		} catch (RedisException e) {
			LoggerUtil.error(RecordShareInfoCacheService.class, "redis error :" + e.toString());
			return SysCodeConstant.REDIS_ERROR;
		}
	}

	private int validateUniqulyIdExsit(String uniqulyId) {
		try {
			if (recordShareInfoCache.existKey(SysCodeConstant.REDIS_KEY_SHARE_USER_LOG + uniqulyId)) {
				return SysCodeConstant.SUCCESS;
			} else {
				try {
					ShareUserInfoDO info = queryConfigInfoDao.queryShareUserInfo(uniqulyId);
					if (info == null) { // 不存在写入
						return SysCodeConstant.UNIQULYID_ERROR;
					} else { // 存在记录到缓存中
						return SysCodeConstant.SUCCESS;
					}
				} catch (Exception e) {
					LoggerUtil.error(RecordShareInfoCacheService.class,
							"execute insertShareUserInfo error:" + e.toString());
					return SysCodeConstant.DB_ERROR;
				}

			}
		} catch (RedisException e) {
			LoggerUtil.error(RecordShareInfoCacheService.class, "redis error :" + e.toString());
			return SysCodeConstant.REDIS_ERROR;
		}
	}

	/**
	 * 长url转短url业务层
	 * 每个不同的长url会进行MD5加密，加密结果作为key，然后存入redis中
	 * @param shortUrl
	 * @return
	 */
    public int getShortUrl(ShortUrlVO shortUrlVO, String sign) {
		try{
//			if(!MD5Utils.getMd5(shortUrlVO.getOriginalUrl()).equals(sign)){
//				return SysCodeConstant.SIGN_ERROR;
//			}
			//考虑到一种特殊情况，加入新浪的服务有问题，或者转换的短url无法使用，我们就需要关闭这项服务，
			//做个假关闭，通过数据库配置开关，如果关闭，则直接将长url返给客户端，这样客户端不需要做调整，服务器可以通过修改配置来控制
			//open表示开启，close表示关闭
			String shortUrlSwitch = (String) CommonCache.getConfigInfo(SysCodeConstant.SINA_SHORTURL_SWITCH);
			if(StringUtils.isEmpty(shortUrlSwitch)) {
				return SysCodeConstant.CONFIG_SHORT_URL_ERROR;
			}
			//如果关闭了调用新浪api，说明我们的服务暂时无法正常使用
			if(SysCodeConstant.SWITCH_CLOSE.equals(shortUrlSwitch)) {
				//此时将长url直接返回给客户端,保险起见redis都不能查，
				shortUrlVO.setShortUrl(shortUrlVO.getOriginalUrl());
			}else {
				//1、查redis是否有，如果可以作为返回数据了
				String key = SysCodeConstant.REDIS_KEY_SHORT_URL+MD5Utils.getMd5(shortUrlVO.getOriginalUrl());
				String url = redisClientTemplate.get(key);
				//2、如果没有，则需要调用新浪的api将长url转成短url并保存
				if(StringUtils.isEmpty(url)) {
					String shortUrl = CreateShortUrlFunction.getShortUrl(shortUrlVO.getOriginalUrl());
					if(StringUtils.isEmpty(shortUrl)) {//新浪返回空了，此时还是用原始的长地址
						shortUrlVO.setShortUrl(shortUrlVO.getOriginalUrl());
					}else {
						shortUrlVO.setShortUrl(shortUrl);
						//并存入redis
						redisClientTemplate.set(key, shortUrl);
					}
				}else {//将redis里保存的结果返回给客户端
					shortUrlVO.setShortUrl(url);
				}
			}
			return SysCodeConstant.SUCCESS;
		}catch (Exception e) {
			LoggerUtil.error(RecordShareInfoCacheService.class, "deal getShortUrl error param : shortUrlVO="+shortUrlVO+",error info:"+e.toString());
			return SysCodeConstant.SYSTEM_ERROR;
		}
    }

	/**
	 * 统计该用户累计获得奖励的次数，以及当日累计的奖励的次数
	 * sys_click_share_info表记录的就是每次赠送的记录，以roleId和gameId唯一确定一个分享者
	 * @param form
	 * @param rt
	 * @return
	 */
	public int countRewardTimes(AwardWinnerForm form, RewardTimesVO rt) {
		try {
			if (!MD5Utils.getMd5(form.getPassportName() + form.getGameId() + form.getGatewayId()).equals(form.getSign())) {
				return SysCodeConstant.SIGN_ERROR;
			}
			//查询该用户的累计奖励次数
			int sum = queryConfigInfoDao.querySumCount(form);
			rt.setSumTimes(sum);
			//查询该用户当日累计奖励次数
			int daily = queryConfigInfoDao.queryDailyCount(form);
			rt.setDailyTimes(daily);
			return SysCodeConstant.SUCCESS;
		}catch (Exception e){
			LoggerUtil.error(RecordShareInfoCacheService.class, "deal countRewardTimes error param : form="+form+",error info:"+e.toString());
			return SysCodeConstant.SYSTEM_ERROR;
		}
	}
}
