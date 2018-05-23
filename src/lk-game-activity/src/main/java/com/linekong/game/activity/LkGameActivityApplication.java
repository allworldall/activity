package com.linekong.game.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableCaching
@EnableEncryptableProperties
@EnableScheduling
public class LkGameActivityApplication {

	public static void main(String[] args) {
			//初始化Log4j
			String log4jPath = System.getenv("LK_GAME_ACTIVITY")+File.separator+"log4j.properties";
			if(System.getenv("LK_GAME_ACTIVITY") == null){
				System.err.println("LK_GAME_ACTIVITY 环境变量没有配置");
				System.exit(0);
			}
			if(!Files.exists(Paths.get(log4jPath))){
				System.err.println("路径："+log4jPath+" log4j配置文件不存在");
				System.exit(0);
			}
//			PropertyConfigurator.configure(log4jPath);
//			SpringApplication.run(LkGameActivityApplication.class, args);

		//*** 抽取配置文件  *******
		//读取application.properties文件
		String appPath = System.getenv("LK_GAME_ACTIVITY")+File.separator+ "application.properties";
		if(!Files.exists(Paths.get(appPath))){
			System.err.println("路径："+appPath+" application.properties配置文件不存在");
			System.exit(0);
		}
		Properties properties = new Properties();
		try {
			File appFile = new File(appPath);
			properties.load(new FileInputStream(appFile));

			SpringApplication springApplication = new SpringApplication(LkGameActivityApplication.class);

			springApplication.setDefaultProperties(properties);

			PropertyConfigurator.configure(log4jPath);
			//启动服务器
			springApplication.run(args);
		} catch (IOException e) {
			System.err.println("路径：" + appPath + " application配置文件读取异常");
			System.exit(0);
		}
	}
}
