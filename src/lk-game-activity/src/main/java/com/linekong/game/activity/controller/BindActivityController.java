package com.linekong.game.activity.controller;

import com.linekong.game.activity.pojo.DO.CheckPhoneCodeDO;
import com.linekong.game.activity.pojo.DO.SendPhoneCodeDO;
import com.linekong.game.activity.service.BindActService;
import com.linekong.game.activity.utils.Common;
import com.linekong.game.activity.utils.exceptionUtil.ParamException;
import com.linekong.game.activity.utils.SysCodeConstant;
import com.linekong.game.activity.utils.annotation.support.ValidateService;
import com.linekong.game.activity.utils.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此类手机号绑定活动,包括发送验证码和校验验证码
 */
@Controller
@RequestMapping("/bind")
public class BindActivityController extends BaseController{
    @Autowired
    private BindActService bindActService;


    /**
     * 发送验证码给用户
     * @param request
     * @param response
     * @param sendPhoneCode
     */
    @RequestMapping(value = "/sendPhoneCode", method = RequestMethod.POST)
    public void sendPhoneCode(HttpServletRequest request, HttpServletResponse response ,
                         SendPhoneCodeDO sendPhoneCode) {
        int  result = 0;
        try {
            //请求参数校验
            ValidateService.valid(sendPhoneCode);
            //业务处理都放在service层，并采用错误码的形式，此处将IP放到参数中，暂时只是日志需要
            result = bindActService.sendCode(sendPhoneCode, Common.getTrueIP(request));
        }catch(ParamException e) {
            LoggerUtil.error(this.getClass(), "receive sendCode request param:"+sendPhoneCode + ",error info:" + e.toString());
            result = SysCodeConstant.ERROR_PARAM;
        }catch(Throwable e){
            LoggerUtil.error(this.getClass(), "deal sendCode param:" + sendPhoneCode + ",error info:" + e.toString());
            result = SysCodeConstant.ERROR_SYSTEM;
        }finally {
            response(response, String.valueOf(result));
        }
    }


    /**
     * 校验验证码,成功后即完成绑定
     * @param request
     * @param response
     * @param checkPhoneCode
     */
    @RequestMapping(value = "/checkPhoneCode", method = RequestMethod.POST)
    public void checkPhoneCode(HttpServletRequest request, HttpServletResponse response ,
                               CheckPhoneCodeDO checkPhoneCode) {
        int  result = 0;
        try{
            //请求参数校验
            ValidateService.valid(checkPhoneCode);
            //业务处理都放在service层，并采用错误码的形式，此处将IP放到参数中，暂时只是日志需要
            result = bindActService.checkPhoneCode(checkPhoneCode, Common.getTrueIP(request));
        }catch(ParamException e) {
            LoggerUtil.error(this.getClass(), "receive checkPhoneCode request param:"+checkPhoneCode + ",error info:" + e.toString());
            result = SysCodeConstant.ERROR_PARAM;
        }catch(Throwable e){
            LoggerUtil.error(this.getClass(), "deal checkPhoneCode param:" + checkPhoneCode + ",error info:" + e.toString());
            result = SysCodeConstant.ERROR_SYSTEM;
        }finally {
            response(response, String.valueOf(result));
        }
    }

}
