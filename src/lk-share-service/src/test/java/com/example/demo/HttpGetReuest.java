package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpGetReuest implements Runnable {

	private String url;
	private String param;

	public HttpGetReuest(String url, String param) {
		this.url = url;
		this.param = param;
	}

	@Override
	public void run() {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			System.out.println(urlNameString);
			URL realUrl = new URL(urlNameString);

			System.out.println(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
		//	Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			// System.out.println("1111"+result);
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
