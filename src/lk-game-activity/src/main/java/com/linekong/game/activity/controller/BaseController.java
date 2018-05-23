package com.linekong.game.activity.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
/**
 * 统一返接口返回值接口所有Contoller层的类都继承此方法
 */
public class BaseController {
	
	private static final Logger log = Logger.getLogger(BaseController.class);
	/**
	 * 采用默认UTF-8字符集
	 * @param HttpServletResponse response
	 * @param String result
	 *               返回值
	 */
	public void response(HttpServletResponse response,String result) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(result);
		} catch (IOException e) {
			log.error("response error message:"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 自定义返回字符集格式
	 * @param HttpServletResponse response
	 * @param String result
	 *               返回值
	 * @param String charset
	 *               字符集
	 */
	public void response(HttpServletResponse response,String result,String charset) {
		try {
			response.setCharacterEncoding(charset);
			response.getWriter().print(result);
		} catch (IOException e) {
			log.error("response error message:"+e.getMessage());
			e.printStackTrace();
		}
	}

}
