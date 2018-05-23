package com.linekong.share.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linekong.share.service.pojo.form.AwardWinnerForm;
import com.linekong.share.service.pojo.vo.RecordShareInfoRespVO;
import com.linekong.share.service.pojo.vo.RewardTimesVO;
import com.linekong.share.service.pojo.vo.ShortUrlVO;
import com.linekong.share.service.utils.DataFormatUtils.JsonUtil;
import com.linekong.share.service.utils.DataFormatUtils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linekong.share.service.pojo.form.RecordUserShareInfoForm;
import com.linekong.share.service.pojo.form.ClickShareInfoRecordForm;
import com.linekong.share.service.service.RecordShareInfoCacheService;
import com.linekong.share.service.utils.IPUtils;
import com.linekong.share.service.utils.SysCodeConstant;
import com.linekong.share.service.utils.annotation.support.ValidateService;
import com.linekong.share.service.utils.log.LoggerUtil;

@Controller
public class RecordShareInfoController extends BaseController{

	@Autowired
	private RecordShareInfoCacheService recordShareInfoCacheService;
	@RequestMapping("/recordUserShareInfo")
	public void recordShareInfo(HttpServletRequest request,HttpServletResponse response ,RecordUserShareInfoForm form) {
		RecordShareInfoRespVO resp = new RecordShareInfoRespVO();
		try {
			ValidateService.valid(form);
			recordShareInfoCacheService.recordShareUserInfo(form, resp);
		} catch (Exception e) {
			LoggerUtil.error(RecordShareInfoController.class, "recordShareInfo error:" + e.toString());
			resp.setResult(SysCodeConstant.PARAM_ERROR);
		}finally {
			this.response(response, JsonUtil.convertBeanToJson(resp));
		}
	}
	
	@RequestMapping("/clickShareInfo")
	public void clickShareInfo(HttpServletRequest request,HttpServletResponse response ,ClickShareInfoRecordForm form) {
		try {
			ValidateService.valid(form);
			form.setClickIp(IPUtils.getTrueIP(request)); //设置获取用户浏览器IP
			form.setClickUa(IPUtils.getUserAgent(request));//设置获取用户的user-agent
		} catch (Exception e) {
			LoggerUtil.error(RecordShareInfoController.class, e.toString());
			this.response(response, SysCodeConstant.PARAM_ERROR);
			return;
		}
		this.response(response, recordShareInfoCacheService.recordShareClickInfo(form));
	}

	/**
	 * 因分享链接的长度可能会非常长，为防止用户一条短信容不下，提供此接口
	 * 请求参数为原始链接，响应内容为压缩后的链接
	 * @param request
	 * @param response
	 * @param originalUrl
	 */
	@RequestMapping("/getShortUrl")
	public void getShortUrl(HttpServletRequest request,HttpServletResponse response , String originalUrl, String sign) {
		int result = 0;
		ShortUrlVO shortUrlVO = new ShortUrlVO(originalUrl);
		try{
			if(StringUtils.isEmpty(originalUrl) || StringUtils.isEmpty(sign)) { //判断传入的初始url是否为空
				result = SysCodeConstant.PARAM_ERROR;
				return;
			}
			result = recordShareInfoCacheService.getShortUrl(shortUrlVO, sign);
		} catch (Exception e) {
			result = SysCodeConstant.SYSTEM_ERROR;
			LoggerUtil.error(this.getClass(), "recieve getShortUrl request param:" + originalUrl + ",error info:" + e.toString());
		}finally{
			shortUrlVO.setResult(result);
			response(response, JsonUtil.convertBeanToJson1(shortUrlVO));
		}
	}

	/**
	 * 统计分享者当日奖励次数和累计奖励次数
	 */
	@RequestMapping("/countRewardTimes")
	public void countRewardTimes(HttpServletRequest request, HttpServletResponse response ,AwardWinnerForm form) {
		int result = 0;
		RewardTimesVO rt = new RewardTimesVO();
		try{
			ValidateService.valid(form);
			result = recordShareInfoCacheService.countRewardTimes(form, rt);
		}catch (Exception e) {
			LoggerUtil.error(RecordShareInfoController.class, e.toString());
			result = SysCodeConstant.PARAM_ERROR;
		}finally {
			rt.setResult(result);
			response(response, JsonUtil.convertBeanToJson(rt));
		}
	}
}
