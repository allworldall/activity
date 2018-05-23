package com.linekong.game.activity.service;

import com.linekong.game.activity.dao.model.BindActivityDao;
import com.linekong.game.activity.dao.model.InviteActivityDao;
import com.linekong.game.activity.pojo.DO.CheckPhoneCodeDO;
import com.linekong.game.activity.pojo.DO.LoginInfoDO;
import com.linekong.game.activity.pojo.DO.SendPhoneCodeDO;
import com.linekong.game.activity.pojo.PO.AccountInfoPO;
import com.linekong.game.activity.pojo.PO.PhoneInfoPO;
import com.linekong.game.activity.redis.RedisClientTemplate;
import com.linekong.game.activity.utils.Common;
import com.linekong.game.activity.utils.DataFormatUtils.StringUtils;
import com.linekong.game.activity.utils.SendShortMessage;
import com.linekong.game.activity.utils.SysCodeConstant;
import com.linekong.game.activity.utils.cache.GuavaUtil;
import com.linekong.game.activity.utils.sign.MD5Util;
import com.linekong.game.activity.service.async.SendItemThread;
import com.linekong.game.activity.utils.thread.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 绑定手机号相关业务层
 */
@Service
public class BindActService {

    @Autowired
    BindActivityDao bindActivityDao;

    @Autowired
    InviteActivityDao inviteActivityDao;

    @Autowired
    RedisClientTemplate redisClientTemplate;

    /**
     * 发送验证码业务层
     * @param phone
     * @return
     *
     * 事务传播行为：如果有事务, 那么加入事务, 没有的话新建一个(默认情况下)
     */
    @Transactional(rollbackFor=java.lang.Throwable.class, propagation= Propagation.REQUIRED)
    public int sendCode(SendPhoneCodeDO sendPhoneCode, String remoteIp) throws Exception {
        String phoneNum = sendPhoneCode.getPhoneNum();
        //验签
        if(!MD5Util.getMD5(phoneNum + sendPhoneCode.getAppId()).equals(sendPhoneCode.getSign())){
            return SysCodeConstant.ERROR_SIGN;
        }
        //先查询该手机号是否已经绑定，同一款游戏同一个用户只能绑定一次，直接在绑定关联表里去查询即可，为了程序的扩展型，暂时先默认为换了另一款游戏可以再绑定，
        int count = bindActivityDao.countBind(Integer.parseInt(sendPhoneCode.getAppId()), phoneNum);
        if(count >0){  //说明已经绑定过了，直接返回
            return SysCodeConstant.ERROR_PHONE_REPEAT_BIND;
        }else {
            //生成验证码
            //查询验证码长度
            String lengthCode = GuavaUtil.getConfigValue(SysCodeConstant.LENGTH_VALIDATE_CODE);
            if(StringUtils.isEmpty(lengthCode)){
                return SysCodeConstant.ERROR_CONFIG_VALIDATE_LENGTH;
            }
            String phoneCode = Common.getPhoneCode(Integer.parseInt(lengthCode));
            //保存验证码，先存入redis，再存入数据库
            //1、先查询配置的过期时间
            String expireTime = GuavaUtil.getConfigValue(SysCodeConstant.EXPIRE_REDIS_VALIDATE_CODE);
            if(StringUtils.isEmpty(expireTime)){
                return SysCodeConstant.ERROR_CONFIG_VALIDATE_EXPIRED;
            }
            long expire = Long.parseLong(expireTime);
            //2、存入redis,先查询增量，增量为0，则表示没有保存
            String incrKey = SysCodeConstant.REDIS_INCR_PREFIX + phoneNum + sendPhoneCode.getAppId();
            String incrValue = redisClientTemplate.get(incrKey);
            if(incrValue == null || Integer.parseInt(incrValue) == 0){
                redisClientTemplate.incr(incrKey, 1L, expire);
                //将验证码存入redis
                String messageKey = SysCodeConstant.REDIS_MESSGAGE_PREFIX + phoneNum + sendPhoneCode.getAppId();
                String messageValue = phoneCode;
                redisClientTemplate.set(messageKey, messageValue, expire);
            }else { //直接返回
                return SysCodeConstant.ERROR_CONCURRENT_RQUEST;
            }
            //2、存入数据库
            //保存到记录验证码的日志表里
            bindActivityDao.insertLogTable(phoneNum, phoneCode);
            //保存到验证码信息表里（有验证码状态字段）
            bindActivityDao.insertPhoneCode(phoneNum, phoneCode);
            //发送验证码，暂时不考虑发送失败重发的情况，前端会控制60S之后用户可以再次请求次接口，此处只需注意请求服务商的超时时间即可
            //并返回业务层状态码
            return SendShortMessage.sendMessage(phoneNum, phoneCode);
            //异步发送短信验证码,可以考虑把发起异步时机放在存入数据库之前，这样发送短信和插入数据库可以并行，任何一条线路报错，事务都会回滚
//            FutureTask<Integer> ft = new FutureTask<Integer>(new SendMessageThread(phoneNum, phoneCode));
//            ThreadPoolUtil.pool.submit(ft);
//            return ft.get();
        }
    }

    /**
     * 校验验证码业务层
     * @param checkPhoneCode
     * @param trueIP
     * @return
     * 事务传播行为：如果有事务, 那么加入事务, 没有的话新建一个(默认情况下)
     */
    @Transactional(rollbackFor=java.lang.Throwable.class, propagation= Propagation.REQUIRED)
    public int checkPhoneCode(CheckPhoneCodeDO checkPhoneCode, String trueIP) {
        //验签
        if (!MD5Util.getMD5(checkPhoneCode.getPhoneNum() + checkPhoneCode.getPhoneCode() +
                checkPhoneCode.getGameId()).equals(checkPhoneCode.getSign())){
            return SysCodeConstant.ERROR_SIGN;
        }
        //校验手机号是否合法
        String phoneNum = checkPhoneCode.getPhoneNum();
        //防并发
        //先查询增量，增量为0，则表示没有保存
        String incrKey = SysCodeConstant.PREVENT_CONCURRENT_CHECK_CODE + phoneNum + checkPhoneCode.getPhoneCode();
        String incrValue = redisClientTemplate.get(incrKey);
        if(incrValue == null || Integer.parseInt(incrValue) == 0) {
            //1、先查询配置的过期时间
            String expireTime = GuavaUtil.getConfigValue(SysCodeConstant.EXPIRE_REDIS_VALIDATE_CODE);
            if(StringUtils.isEmpty(expireTime)){
                return SysCodeConstant.ERROR_CONFIG_VALIDATE_EXPIRED;
            }
            long expire = Long.parseLong(expireTime);
            redisClientTemplate.incr(incrKey, 1L, expire);
        }else {  //直接返回
            return SysCodeConstant.ERROR_CONCURRENT_RQUEST;
        }
        //查询该手机号是否已经绑定，同一款游戏同一个用户只能绑定一次，直接在绑定关联表里去查询即可，为了程序的扩展型，暂时先默认为换了另一款游戏可以再绑定，
        int bind = bindActivityDao.countBind(Integer.parseInt(checkPhoneCode.getAppId()), phoneNum);
        if(bind >0){  //说明已经绑定过了，直接返回
            return SysCodeConstant.ERROR_PHONE_REPEAT_BIND;
        }
        //查redis
        String messageKey = SysCodeConstant.REDIS_MESSGAGE_PREFIX + phoneNum + checkPhoneCode.getGameId();
        String phoneCode = redisClientTemplate.get(messageKey);
        if(phoneCode != null){//不为空分两种情况，验证码匹配和不匹配
            if(!checkPhoneCode.getPhoneCode().equals(phoneCode)){ //验证码输入错误
                return SysCodeConstant.ERROR_PHONE_CODE_MATCH;
            }
        }else { //有可能验证码过期，有可能存入redis异常，此时需要查一下数据库
            //查询库里是否有匹配的验证码
            int count = bindActivityDao.countMatchCode(checkPhoneCode.getPhoneCode(), phoneNum);
            if(count == 0){ //redis和库里都没有
                return SysCodeConstant.ERROR_PHONE_CODE_UNEXIST;
            }
        }
        /*如果匹配上了，则验证通过，需要对几张表进行写操作
         *插入手机信息表，并返回主键，默认sys_phone_info表里维护的手机号不会重复，维护着所有手机号信息
         *因此可先对该表进行查询,获取主键
         */
        PhoneInfoPO phoneInfoPO = bindActivityDao.getPhoneInfo(checkPhoneCode.getPhoneNum());
        if(phoneInfoPO == null || phoneInfoPO.getPhoneId() == 0) { //说明这手机号没有绑定过,
            //此时需要插入一条记录，并返主键
            phoneInfoPO = new PhoneInfoPO(phoneNum);
            bindActivityDao.insertPhoneInfo(phoneInfoPO);
        }//此处无需再进行else查询绑定关联表主键是否存在，因为前面查询用户是否绑定时就具备此功能
        //插入账号信息表，并返回主键
        //创建账号实体类
        AccountInfoPO accountInfoPO = AccountInfoPO.createPO(checkPhoneCode);
        //防止唯一索引冲突
        AccountInfoPO tempPO = bindActivityDao.getAccountInfoPO(accountInfoPO);
        if(tempPO != null && tempPO.getPassportId() != 0) {
            accountInfoPO = tempPO;
        }else {
            //保存保定账号
            bindActivityDao.insertAccountInfo(accountInfoPO);
        }
        //插入账号和手机号绑定关联表
        bindActivityDao.insertBindInfo(phoneInfoPO.getPhoneId(), accountInfoPO.getPassportId(), Integer.parseInt(checkPhoneCode.getAppId()));
        //删除验证码状态,sys_validate_code表
        bindActivityDao.deletePhoneCode(phoneNum);
        //完成了DB操作之后，再将redis存储的数据清除，保证已经绑定的验证码不再使用
        redisClientTemplate.remove(messageKey);
        //将绑定账号存入redis，供上报通讯录使
        redisClientTemplate.setAdd(SysCodeConstant.REDIS_BINDED_PHONE+checkPhoneCode.getAppId(), phoneNum);
        //记录一次用户登录信息
        LoginInfoDO loginInfoDO = new LoginInfoDO();
        loginInfoDO.packageData(checkPhoneCode);
        inviteActivityDao.recordLoginInfo(loginInfoDO);
        //异步查询该用户是不是被邀请的，如果是，则需要给邀请人发奖励，但是这个过程不应该对用户绑定成功有影响
        ThreadPoolUtil.pool.execute(new SendItemThread(checkPhoneCode, phoneNum, Integer.parseInt(checkPhoneCode.getAppId()), bindActivityDao));
        return SysCodeConstant.DEAL_SUCCESS;
    }

}
