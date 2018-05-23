package com.linekong.report.reyun.web;

import com.linekong.report.reyun.util.log.LoggerUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


public class BaseController {
	
	/**
	 * 所有Controller 继承此类返回结果
	 * @param HttpServletResponse response
	 * @param String			  result
	 */
	protected void response(HttpServletResponse response,String result){
		try {
			response.setContentType("text/html;charset=UTF-8"); //词句为新加，因为在controller层有一些渠道加了这句代码
			response.getWriter().print(result);
		} catch (IOException e) {
			LoggerUtil.error(BaseController.class, e.getMessage());
		}
		
	}
}
