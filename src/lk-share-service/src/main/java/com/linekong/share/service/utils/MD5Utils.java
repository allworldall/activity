package com.linekong.share.service.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
	/**
	 * 生成MD5值
	 * @param String str
	 * @return String
	 */
	public static String getMd5(String str) {
		return DigestUtils.md5Hex(str);
	}
	
	public static void main(String[] args) {
		System.out.println(getMd5("a218c980012318aac9f80aca6e67974cabc"));
	}

}
