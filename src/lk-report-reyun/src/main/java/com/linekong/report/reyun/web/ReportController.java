package com.linekong.report.reyun.web;

import com.linekong.report.reyun.pojo.ChargeInfoFormBean;
import com.linekong.report.reyun.service.ReportReyunService;
import com.linekong.report.reyun.util.Common;
import com.linekong.report.reyun.util.Constant;
import com.linekong.report.reyun.util.StringUtils;
import com.linekong.report.reyun.util.annotation.support.ValidateService;
import com.linekong.report.reyun.util.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ReportController extends BaseController{

    @Autowired
    public ReportReyunService reportReyunService;

    @RequestMapping("/reportReyunWithCharge")
    public void noticeReyunWithCharge(ChargeInfoFormBean form, HttpServletResponse response, HttpServletRequest request){
        int resultCode = 0;
        try {
            //验证参数，并且userID和userName必须传过来一个
            ValidateService.valid(form);
            if(StringUtils.isEmpty(form.getUserId()) && StringUtils.isEmpty(form.getUserName())){
                throw new Exception("userId 、userName 必须要传一个");
            }
            //通知热云
            resultCode = reportReyunService.reportReyunWithCharge(form, Common.getTrueIP(request));
        } catch (Exception e) {
            LoggerUtil.error(this.getClass(), "receive report ReyunWithCharge request param error:"+form.toString()+",error info:"+e.toString());
            resultCode = Constant.ERROR_PARAM_VALIDATE;
        }finally {
            this.response(response, resultCode+"");
        }
    }

}
