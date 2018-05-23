package com.linekong.game.activity.service;

import com.linekong.game.activity.dao.model.BindActivityDao;
import com.linekong.game.activity.dao.model.InviteActivityDao;
import com.linekong.game.activity.pojo.DO.*;
import com.linekong.game.activity.pojo.PO.AccountInfoPO;
import com.linekong.game.activity.pojo.VO.BindedPhoneVO;
import com.linekong.game.activity.pojo.VO.BindedPhoneVOList;
import com.linekong.game.activity.pojo.VO.SelfInvitePhonesVO;
import com.linekong.game.activity.redis.RedisClientTemplate;
import com.linekong.game.activity.utils.DataFormatUtils.RegexUtil;
import com.linekong.game.activity.utils.DataFormatUtils.StringUtils;
import com.linekong.game.activity.utils.SysCodeConstant;
import com.linekong.game.activity.utils.log.LoggerUtil;
import com.linekong.game.activity.utils.sign.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 邀请相关的业务层，如查询哪些手机号已经绑定，给用户发送邀请
 */
@Service
public class InviteActService {

    @Autowired
    InviteActivityDao inviteActivityDao;

    @Autowired
    BindActivityDao bindActivityDao;

    @Autowired
    RedisClientTemplate redisClientTemplate;
    /**
     * 登录接口业务层，只保存最近一次登录时间,且只记录绑定过手机号的用户
     * @param loginInfoDO
     */
    @Transactional(rollbackFor=java.lang.Throwable.class, propagation= Propagation.REQUIRED)
    public int recordLoginInfo(LoginInfoDO loginInfoDO, String trueIp) {
        //验签
        if(!MD5Util.getMD5(loginInfoDO.getAccount()+loginInfoDO.getGameId()).equals(loginInfoDO.getSign())){
            return SysCodeConstant.ERROR_SIGN;
        }
        //先判断手机号是否为空，如果不为空，可以直接入库了，采用merge方式入库
        if(!StringUtils.isEmpty(loginInfoDO.getPhoneNum())) {
            if(!RegexUtil.isPhoneNo(loginInfoDO.getPhoneNum())){
                return SysCodeConstant.ERROR_PHONE_NUM;
            }
            //先查redis的绑定手机号集合里是否有该手机号
            if(!redisClientTemplate.isMember(SysCodeConstant.REDIS_BINDED_PHONE + loginInfoDO.getAppId(), loginInfoDO.getPhoneNum())) {
                //再查询数据库该手机号是否绑定，如果没有绑定，则不计入登陆信息
                int bind = bindActivityDao.countBind(Integer.parseInt(loginInfoDO.getAppId()), loginInfoDO.getPhoneNum());
                if(bind < 1){  //说明未绑定，直接返回
                    return SysCodeConstant.ERROR_PHONE_NOT_BIND;
                }
            }
            //到此处说明该手机号已绑定，再查询绑定时的账号和本接口传过来的账号是否是同一个，如果不是同一个则需要将新传过来的账号也和手机号做一个绑定，本步骤是为了完善绑定关系，尽量将玩家的各类账号都和手机号建立好绑定关系
            AccountInfoPO accountInfoPO = AccountInfoPO.createPO(loginInfoDO.getAccount(), loginInfoDO.getAccountType(),
                    loginInfoDO.getGameId(), loginInfoDO.getRoleId());
            if(bindActivityDao.getAccountInfoPO(accountInfoPO) == null) { //用户当时绑定的账号和这此登录的账号不一样，所以再将此账号和手机号建立绑定关系
                //保存账号
                bindActivityDao.insertAccountInfo(accountInfoPO);
                //保存绑定关系,再写一种insert语法，insert中带select的
                bindActivityDao.insertBindInfoElse(accountInfoPO.getPassportId(), loginInfoDO.getPhoneNum(), loginInfoDO.getAppId());
            } //else情况不用考虑，说明已经保存了

            //redis里有或者库里有，都需记录登录信息
            inviteActivityDao.recordLoginInfo(loginInfoDO);
        }else{ //如果手机号为空，则需要通过玩家的绑定信息表里查看有无手机号，如果有，说明绑定过，查出手机号并记录登录数据
            List<String> phoneNumList = inviteActivityDao.getPhoneNum(loginInfoDO);
            if(phoneNumList == null || phoneNumList.size() == 0){
                return SysCodeConstant.ERROR_PHONE_NOT_BIND;
            }else if(phoneNumList.size()>1){
                //供开发人员调查,目前是允许一个账号绑定多个手机号的
                LoggerUtil.info(this.getClass(), "InviteActivityDao.getPhoneNum() method get much result="+phoneNumList);
                //记录登录信息
//                loginInfoDO.setPhoneNum(phoneNumList.get(0));
                inviteActivityDao.recordLoginInfo(loginInfoDO);
            }else{
                //记录登录信息
//                loginInfoDO.setPhoneNum(phoneNumList.get(0));
                inviteActivityDao.recordLoginInfo(loginInfoDO);
            }
        }
        return SysCodeConstant.DEAL_SUCCESS;
    }

    /**
     * 检查通讯录
     * @param addrListDO
     * @param list
     * @return
     */
    @Transactional(rollbackFor=java.lang.Throwable.class, propagation= Propagation.REQUIRED)
    public int checkAddrList(AddrDOList addrListDO, BindedPhoneVOList bpl, String trueIP) {
        if(!MD5Util.getMD5(addrListDO.getAccount()+addrListDO.getGameId()).equals(addrListDO.getSign())){
            return SysCodeConstant.ERROR_SIGN;
        }
        List<AddrDO> listBinded;//封装所有已绑定的手机号
        if(addrListDO.getData() == null || addrListDO.getData().size() == 0){
            LoggerUtil.info(this.getClass(), "report address phoneNums empty");
            return SysCodeConstant.DEAL_SUCCESS;
        }
        //记录上报通讯录数据,采用批处理
        inviteActivityDao.recordReportData(addrListDO);
        //筛选已绑定的手机号
        //1、获取通讯录所有手机号
        Set<String> phoneNums = addrListDO.getData().parallelStream().map(AddrDO::getPhoneNum).collect(Collectors.toSet());
        //2、与redis取交集
        Set<String> repeatPhoneNum = redisClientTemplate.innerSet(phoneNums, SysCodeConstant.REDIS_BINDED_PHONE+addrListDO.getAppId());
        if(repeatPhoneNum != null && repeatPhoneNum.size() > 0) {
//            //将已绑定的手机号封装到List<AddrDO>中
            listBinded = addrListDO.getData().parallelStream().filter(addrDO -> repeatPhoneNum.contains(addrDO.getPhoneNum())).collect(Collectors.toList());
        }else{
            //redis里没有查出交集，此时去数据库查询看有没有交集，采用批量查询
            Set<String> repeatPhoneNum2 = inviteActivityDao.queryBindedPhone(phoneNums, Integer.parseInt(addrListDO.getAppId()));
            if(repeatPhoneNum2 == null || repeatPhoneNum2.size() == 0){
                //此时返回前端的手机号为空，如果前端需要返回其它的错误码，在此处更改即可，别忘了controller层改一下对应的响应的messgage
                return SysCodeConstant.DEAL_SUCCESS;
            }else {
//                //将已绑定的手机号封装到List<AddrDO>中
                listBinded = addrListDO.getData().parallelStream().filter(addrDO -> repeatPhoneNum2.contains(addrDO.getPhoneNum())).collect(Collectors.toList());
            }
        }
        //遍历上述集合，并将每个元素对应的登录时间和角色Id封装到<BindedPhoneVO>中
        List<BindedPhoneVO> list = listBinded.parallelStream().map(addrDO -> inviteActivityDao.getBindedPhoneVO(addrDO)).collect(Collectors.toList());
        bpl.setData(list);
        return SysCodeConstant.DEAL_SUCCESS;
    }

    /**
     * 客户端向用户发送邀请时，服务器记录邀请信息，
     * @param inviteInfoDO
     * @return
     */
    @Transactional(rollbackFor=java.lang.Throwable.class, propagation= Propagation.REQUIRED)
    public int inviteUser(InviteInfoDO inviteInfoDO, String trueIP) {
        if(!MD5Util.getMD5(inviteInfoDO.getAccount()+inviteInfoDO.getGameId()+inviteInfoDO.getRoleId()).equals(inviteInfoDO.getSign())){
            return SysCodeConstant.ERROR_SIGN;
        }
        //查询被邀请用户是否被邀请，
        String redisInviteKey = SysCodeConstant.REDIS_INVITED_PHONE+inviteInfoDO.getAppId();
        // 先查redis，无，再查数据库
        boolean invited = redisClientTemplate.isMember(redisInviteKey, inviteInfoDO.getInvitedNum());
        if(invited){
            return SysCodeConstant.ERROR_INVITED_REPEAT;//已被邀请过
        }else { //查一下数据库确定该手机号有没有被邀请,查有没有数据就行
            if(inviteActivityDao.countInvite(Integer.parseInt(inviteInfoDO.getAppId()), inviteInfoDO.getInvitedNum())>0){
                redisClientTemplate.setAdd(redisInviteKey, inviteInfoDO.getInvitedNum());
                return SysCodeConstant.ERROR_INVITED_REPEAT;//已被邀请过
            }else { //记录邀请关系，插入邀请关系表
                inviteActivityDao.insertInviteRelation(inviteInfoDO);
                //并将被邀请者存入redis,同一个应用中，一个手机号只能被邀请一次（取最早那次）
                redisClientTemplate.setAdd(redisInviteKey, inviteInfoDO.getInvitedNum());
            }
        }
        return SysCodeConstant.DEAL_SUCCESS;
    }

    /**
     * 返回属于当前用户邀请的手机号
     * 纯查询方法，不需要事务处理
     */
    public int getSelfInvitePhones(CurrenUserDO currenUser, String trueIP, SelfInvitePhonesVO phonesVO) {
        if(!MD5Util.getMD5(currenUser.getAccount()+currenUser.getGameId()).equals(currenUser.getSign())) {
            return SysCodeConstant.ERROR_SIGN;
        }
        //查询当前用户邀请过的用户
        List<String> phones = inviteActivityDao.getSelfInvitePhones(currenUser);
        if(phones == null && phones.size() == 0) {
            //只是库里没有数据，业务处理并没有出错
        }else {
            phonesVO.setPhoneNums(phones.toArray(new String[0]));
        }
        return SysCodeConstant.DEAL_SUCCESS;
    }
}
