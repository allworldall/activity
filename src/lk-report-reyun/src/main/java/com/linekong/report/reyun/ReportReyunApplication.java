package com.linekong.report.reyun;

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

import com.linekong.report.reyun.dao.dynamicdatasource.DynamicDataSourceRegister;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@Import(DynamicDataSourceRegister.class)
public class ReportReyunApplication {

	public static void main(String[] args) {

		//初始化Log4j
		String log4jPath = System.getenv("REPORT_REYUN")+File.separator+"log4j.properties";
		if(System.getenv("REPORT_REYUN") == null){
			System.err.println("REPORT_REYUN 环境变量没有配置");
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
			String appPath = System.getenv("REPORT_REYUN") + File.separator + "application.properties";
			properties.load(new FileInputStream(appPath));
			SpringApplication springApplication = new SpringApplication(ReportReyunApplication.class);
			springApplication.setDefaultProperties(properties);
			springApplication.run(args);//启动应用服务器
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
