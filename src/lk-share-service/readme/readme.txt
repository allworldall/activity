1、本项目需要的Java环境是1.8
2、编译时在maven环境中执行执行以下命令进行编译打包：
  mvn package -Dmaven.test.skip=true
3、由于log4j和application.properties文件从项目中抽取出来
   所以需要在系统中设置环境变量路径，路径名称为 LINE_KONG,
   设置完成环境变量以后将log4j和application.properties文件放到配置的LINE_KONG对应的环境变量目录下
   Linux 可以在用户环境变量 .bash_profile 下设置
   例如：export LINE_KONG=/Users/fangming/config
4、application.properties文件中的数据库密码和redis连接密码需要进行加密
   加密方式如下：
   到项目中的readme下将jasypt-1.9.2.jar放到一个单独的位置
   然后在该jar所在的文件目录下执行以下命令
   java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=需要加密的密码 password=123456 algorithm=PBEWithMD5AndDES
   将需要几码的密码替换成对应的明文密码，然后执行