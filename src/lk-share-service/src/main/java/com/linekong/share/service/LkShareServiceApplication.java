package com.linekong.share.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableCaching
@EnableEncryptableProperties
public class LkShareServiceApplication {
	public static void main(String[] args) {
		//初始化Log4j
		String log4jPath = System.getenv("LINE_KONG")+File.separator+"log4j.properties";
		if(System.getenv("LINE_KONG") == null){
			System.err.println("LINE_KONG 环境变量没有配置");
			System.exit(0);
		}
		if(!Files.exists(Paths.get(log4jPath))){
			System.err.println("路径："+log4jPath+" log4j配置文件不存在");
			System.exit(0);
		}
		PropertyConfigurator.configure(log4jPath);
		//加载非默认位置application.properties配置文件
		Properties properties = new Properties();
		try {
			String appPath = System.getenv("LINE_KONG") + File.separator + "application.properties";
			properties.load(new FileInputStream(appPath));
			SpringApplication springApplication = new SpringApplication(LkShareServiceApplication.class);
			springApplication.setDefaultProperties(properties);
			springApplication.run(args);//启动应用服务器
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//本地测试使用项目中的配置文件打开如下代码注释
	//	SpringApplication.run(LkShareServiceApplication.class, args);
	}
}
