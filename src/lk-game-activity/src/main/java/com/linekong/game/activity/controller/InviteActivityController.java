package com.linekong.game.activity.controller;

import com.linekong.game.activity.pojo.DO.AddrDOList;
import com.linekong.game.activity.pojo.DO.CurrenUserDO;
import com.linekong.game.activity.pojo.DO.InviteInfoDO;
import com.linekong.game.activity.pojo.DO.LoginInfoDO;
import com.linekong.game.activity.pojo.VO.BindedPhoneVOList;
import com.linekong.game.activity.pojo.VO.InviteUserVO;
import com.linekong.game.activity.pojo.VO.SelfInvitePhonesVO;
import com.linekong.game.activity.service.InviteActService;
import com.linekong.game.activity.utils.Common;
import com.linekong.game.activity.utils.DataFormatUtils.JsonUtil;
import com.linekong.game.activity.utils.DataFormatUtils.RegexUtil;
import com.linekong.game.activity.utils.SysCodeConstant;
import com.linekong.game.activity.utils.annotation.support.ValidateService;
import com.linekong.game.activity.utils.exceptionUtil.ParamException;
import com.linekong.game.activity.utils.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/invite")
public class InviteActivityController extends BaseController{
    @Autowired
    InviteActService inviteActService;

    /**
     * 记录已绑定手机号用户登录信息，主要是登录时间
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void recordLoginInfo(HttpServletRequest request, HttpServletResponse response ,
                                LoginInfoDO loginInfoDO){
        int result = 0;
        try{
            ValidateService.valid(loginInfoDO);
            result = inviteActService.recordLoginInfo(loginInfoDO, Common.getTrueIP(request));
        }catch (ParamException e){
            result = SysCodeConstant.ERROR_PARAM;
            LoggerUtil.error(this.getClass(), "receive recordLoginInfo request param:"+loginInfoDO + ",error info:" + e.toString());
        }catch (Exception e){
            result = SysCodeConstant.ERROR_SYSTEM;
            LoggerUtil.error(this.getClass(), "deal recordLoginInfo request param:"+loginInfoDO + ",error info:" + e.toString());
        }finally {
            //不能影响到用户的正常登录
            response(response, String.valueOf(result));
        }
    }

    /**
     * 接口描述：客户端将该玩家的手机通讯录号码和姓名发过来，服务器将已绑定的手机号和姓名,以及角色Id（待议）回传给客户端
     *
     */
    @RequestMapping(value = "/checkAddrList", method = RequestMethod.POST)
    public void checkAddrList(HttpServletRequest request, HttpServletResponse response ,
                              @RequestBody AddrDOList addrListDO) {
        int result = 0;
        BindedPhoneVOList bpList = new BindedPhoneVOList();
        try {
            Optional<AddrDOList> optional = Optional.ofNullable(addrListDO);
            if (!optional.isPresent()) { //未接收到请求数据
                result = SysCodeConstant.ERROR_ENCAPSULATE_PARAM;
                return;
            }
            ValidateService.valid(addrListDO);
            result = inviteActService.checkAddrList(addrListDO, bpList, Common.getTrueIP(request));
        }catch (ParamException e){
            result = SysCodeConstant.ERROR_PARAM;
            LoggerUtil.error(this.getClass(), "receive checkAddrList request param:"+addrListDO + ",error info:" + e.toString());
        }catch (Throwable e){
            result = SysCodeConstant.ERROR_SYSTEM;
            LoggerUtil.error(this.getClass(), "deal checkAddrList param:" + addrListDO + ",error info:" + e.toString());
        }finally {
            BindedPhoneVOList.createResBean(result, bpList);
            response(response, JsonUtil.convertBeanToJson(bpList));
        }
    }

    /**
     * 客户端向用户发送邀请时，服务器记录邀请信息，
     * 因为只有最先邀请的那个人才有奖励，所以当这个手机号有被邀请，后续再被邀请将不存入数据库
     *
     */
    @RequestMapping(value = "/relation", method = RequestMethod.POST)
    public void inviteUser(HttpServletRequest request, HttpServletResponse response ,
                           InviteInfoDO inviteInfoDO){
        int result = 0;
        try {
            //传过来的手机号格式可能千奇百怪，需要先处理一下，再进行注解解析
            inviteInfoDO.setInvitedNum(RegexUtil.filterNum(inviteInfoDO.getInvitedNum()));
            ValidateService.valid(inviteInfoDO);
            result = inviteActService.inviteUser(inviteInfoDO, Common.getTrueIP(request));
        }catch (ParamException e) {
            LoggerUtil.error(this.getClass(), "receive inviteUser request param:"+inviteInfoDO + ",error info:" + e.toString());
            result = SysCodeConstant.ERROR_PARAM;
        }catch (Exception e){
            LoggerUtil.error(this.getClass(), "deal inviteUser param:" + inviteInfoDO + ",error info:" + e.toString());
            result = SysCodeConstant.ERROR_SYSTEM;
        }finally {
            response(response, JsonUtil.convertBeanToJson(new InviteUserVO(result).createResBean()));
        }
    }

    /**
     * 获取属于该用户自己邀请的手机号
     */
    @RequestMapping(value = "/invitePhones", method = RequestMethod.POST)
    public void getSelfInvitePhones(HttpServletRequest request, HttpServletResponse response ,
                                CurrenUserDO currenUser) {
        int result = 0;
        SelfInvitePhonesVO phones = new SelfInvitePhonesVO();
        try{
            ValidateService.valid(currenUser);
            result = inviteActService.getSelfInvitePhones(currenUser, Common.getTrueIP(request), phones);
        }catch (ParamException e) {
            LoggerUtil.error(this.getClass(), "receive getSelfInvitePhones request param:"+currenUser + ",error info:" + e.toString());
            result = SysCodeConstant.ERROR_PARAM;
        }catch (Exception e) {
            LoggerUtil.error(this.getClass(), "deal getSelfInvitePhones param:" + currenUser + ",error info:" + e.toString());
            result = SysCodeConstant.ERROR_SYSTEM;
        }finally {
            phones.setResult(result);
            response(response, JsonUtil.convertBeanToJson(phones));
        }

    }
}
