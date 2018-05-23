package com.example.demo;

import org.junit.Test;

public class RecordShareInfoTest {
	@Test
	public void recordShareTest() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("uniqulyId=").append("123456789").append("&");
		buffer.append("clickIp=").append("192.168.252.44").append("&");
		buffer.append("clickUa=").append("tseasetaettase").append("&");
		buffer.append("clickUrl=").append("www.baidu.com").append("&");
		buffer.append("gameId=").append("0").append("&");
		buffer.append("sign=").append("12312312");
		new Thread(new HttpGetReuest("http://localhost:8080/shareInfo", buffer.toString())).run();
	}

}
