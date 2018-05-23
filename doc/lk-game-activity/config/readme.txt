1、本项目需要的Java环境是1.8
2、编译时在maven环境中执行执行以下命令进行编译打包：
  mvn package -Dmaven.test.skip=true
3、配置环境变量LK_GAME_ACTIVITY，将log4j.properties和application.properties两个文件放在上述环境变量所指的目录下
   更改log4j配置文件的log4j.appender.R.File的值为当前服务器相关的文件路径
4、更改application.properties文件中的端口号、数据库IP、账号等和当前环境一直，且数据库密码和redis连接密码需要进行加密
   加密方式如下：
   到项目中的readme下将jasypt-1.9.2.jar放到一个单独的位置
   然后在该jar所在的文件目录下执行以下命令
   java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="需要加密的密码" password=123456 algorithm=PBEWithMD5AndDES
   将需要加密的密码替换成对应的明文密码，然后执行