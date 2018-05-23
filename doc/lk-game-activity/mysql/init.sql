/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.15-log : Database - game_activity
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`game_activity` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `game_activity`;

/*Table structure for table `log_report_phones` */

DROP TABLE IF EXISTS `log_report_phones`;

CREATE TABLE `log_report_phones` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `app_id` int(11) NOT NULL COMMENT '应用Id',
  `game_id` int(11) NOT NULL COMMENT '游戏Id',
  `passport_name` varchar(200) NOT NULL COMMENT '上报者姓名',
  `name` varchar(200) NULL COMMENT '手机号对应的姓名',
  `phone_num` varchar(200) NULL COMMENT '上报的手机号',
  `report_time` datetime NOT NULL COMMENT '上报时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `log_report_phones` */

/*Table structure for table `log_send_items` */

DROP TABLE IF EXISTS `log_send_items`;

CREATE TABLE `log_send_items` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `activity_id` varchar(50) NOT NULL COMMENT '活动编号',
  `passport_name` varchar(200) NOT NULL COMMENT '账号',
  `game_id` int(11) NOT NULL COMMENT '游戏Id',
  `gateway_id` int(11) NOT NULL COMMENT '区服Id',
  `item_code` varchar(100) NOT NULL COMMENT '赠送物品编号',
  `item_num` int(11) NOT NULL COMMENT '物品数量',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '赠送时间',
  `role_id` bigint(20) NOT NULL COMMENT '角色Id',
  `result` int(11) NOT NULL DEFAULT '-10000' COMMENT '发送返回值',
  `success_time` datetime DEFAULT NULL COMMENT '获取返回成功时间',
  `send_count` int(11) NOT NULL DEFAULT '1' COMMENT '发送次数',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `log_send_items` */

/*Table structure for table `log_validate_code` */

DROP TABLE IF EXISTS `log_validate_code`;

CREATE TABLE `log_validate_code` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `phone_num` varchar(100) NOT NULL COMMENT '手机号',
  `validate_code` varchar(100) NOT NULL COMMENT '验证码',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`log_id`,`phone_num`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8 COMMENT='手机号验证码发送记录表';

/*Data for the table `log_validate_code` */

/*Table structure for table `sys_account` */

DROP TABLE IF EXISTS `sys_account`;

CREATE TABLE `sys_account` (
  `passport_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `passport_name` varchar(200) NOT NULL COMMENT '账号名称',
  `passport_type` int(11) NOT NULL COMMENT '账号类型',
  `role_id` INT(20) NOT NULL  COMMENT '角色id',
  `bind_status` int(11) NOT NULL DEFAULT '1' COMMENT '绑定状态 1绑定 0 未绑定',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '账号状态1正常0冻结',
  `game_id` int(11) NOT NULL DEFAULT '0' COMMENT '游戏id',
  PRIMARY KEY (`passport_id`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8 COMMENT='账号信息表';

/*Data for the table `sys_account` */

/*Table structure for table `sys_bind_info` */

DROP TABLE IF EXISTS `sys_bind_info`;

CREATE TABLE `sys_bind_info` (
  `passport_id` int(11) NOT NULL COMMENT '账号ID',
  `phone_id` int(11) NOT NULL COMMENT '手机号ID',
  `bind_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '绑定时间',
  `app_id` int(11) NOT NULL DEFAULT '0' COMMENT '应用id,一款游戏对应哟个和id',
  PRIMARY KEY (`phone_id`, `passport_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号与手机号绑定关联表';

/*Data for the table `sys_bind_info` */

/*Table structure for table `sys_config_info` */

DROP TABLE IF EXISTS `sys_config_info`;

CREATE TABLE `sys_config_info` (
  `property_key` varchar(200) NOT NULL COMMENT '属性key',
  `property_value` varchar(500) NOT NULL COMMENT '属性值value',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(11) NOT NULL COMMENT '状态1正常，0 停用',
  PRIMARY KEY (`property_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

/*Data for the table `sys_config_info` */

insert  into `sys_config_info`(`property_key`,`property_value`,`create_time`,`status`) values ('length.validate.code','6',now(),1),('max.send.count','3',now(),1),('nntg.giveItem.act1','123456789',now(),1),('phone.messege.account','lgzxhy',now(),1),('phone.messege.content','验证码：xxxxxx，您正在闹闹天宫中绑定手机号码，请在60秒之内输入此验证码完成验证',now(),1),('phone.messege.password','gagyqr82',now(),1),('phone.messege.sign','【蓝港互动】',now(),1),('phone.messege.url','http://apisms.kyong.net:8080/eums/sms/send.do',now(),1),('redis.validate.code.expire','60',now(),1),('xml_rpc_address','http://192.168.252.53:18080/etoolkitsweb/xmlRpcServer',now(),1),('xml_rpc_key','linekongkong',now(),1);

/*Table structure for table `sys_invite_relation` */

DROP TABLE IF EXISTS `sys_invite_relation`;

CREATE TABLE `sys_invite_relation` (
  `app_id` int(11) NOT NULL COMMENT '应用Id',
  `game_id` int(11) NOT NULL COMMENT '游戏Id（邀请者的）',
  `passport_name` varchar(200) NOT NULL COMMENT '账号（邀请者的）',
  `gateway_id` int(11) NOT NULL COMMENT '区服Id（邀请者的）',
  `role_id` bigint(20) NOT NULL COMMENT '角色Id（邀请者的）',
  `phone_num` varchar(20) NOT NULL COMMENT '被邀请者手机号',
  `invite_time` datetime NOT NULL COMMENT '邀请时间',
  PRIMARY KEY (`app_id`,`phone_num`,`game_id`,`passport_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_invite_relation` */

/*Table structure for table `sys_item_info` */

DROP TABLE IF EXISTS `sys_item_info`;

CREATE TABLE `sys_item_info` (
  `activity_id` varchar(50) NOT NULL COMMENT '活动号',
  `item_code` varchar(200) NOT NULL COMMENT '物品代码',
  `item_num` int(11) NOT NULL COMMENT '物品数据量',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1、正常    0、冻结配置',
  PRIMARY KEY (`activity_id`,`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_item_info` */

/*Table structure for table `sys_login_info` */

DROP TABLE IF EXISTS `sys_login_info`;

CREATE TABLE `sys_login_info` (
  `app_id` int(11) NOT NULL COMMENT '应用Id，每款游戏是唯一的',
  `game_id` int(11) NOT NULL COMMENT '游戏id',
  `passport_name` varchar(200) NOT NULL COMMENT '账号',
  `passport_type` int(2) NOT NULL COMMENT '账号类型1.QQ  2.微信 9.未知账号类型',
  `role_id` INT(20) NOT NULL  COMMENT '角色Id',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近登录时间',
  PRIMARY KEY (`app_id`,`game_id`,`passport_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_login_info` */

/*Table structure for table `sys_phone_info` */

DROP TABLE IF EXISTS `sys_phone_info`;

CREATE TABLE `sys_phone_info` (
  `phone_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '手机号id',
  `phone_num` varchar(20) NOT NULL COMMENT '手机号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '绑定状态1 正常，0 冻结 ',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`phone_id`),
  UNIQUE KEY `index_id_num` (`phone_num`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8 COMMENT='手机号信息表';

/*Data for the table `sys_phone_info` */

/*Table structure for table `sys_validate_code` */

DROP TABLE IF EXISTS `sys_validate_code`;

CREATE TABLE `sys_validate_code` (
  `phone_num` varchar(100) NOT NULL COMMENT '手机号',
  `validate_code` varchar(100) NOT NULL COMMENT '手机号验证码',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '是否已经使用:1、使用 0、未使用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机号验证码';

ALTER TABLE `game_activity`.`sys_account`   
  ADD  UNIQUE INDEX `index1` (`passport_name`, `game_id`, `passport_type`);

/*Data for the table `sys_validate_code` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
