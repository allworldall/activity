/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.5.15-log : Database - lk-share-service
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`lk-share-service` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `lk-share-service`;

/*Table structure for table `log_click_share_info` */

DROP TABLE IF EXISTS `log_click_share_info`;

CREATE TABLE `log_click_share_info` (
  `uniquly_id` varchar(200) NOT NULL COMMENT '唯一标识',
  `click_ip_num` bigint(20) NOT NULL COMMENT '点击IP',
  `click_ip` varchar(100) NOT NULL,
  `click_ua` varchar(250) NOT NULL COMMENT '点击UA',
  `click_url` varchar(1000) DEFAULT NULL COMMENT '点击URL地址',
  `click_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点击时间',
  PRIMARY KEY (`uniquly_id`,`click_ip_num`,`click_ua`,`click_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `log_send_items` */

DROP TABLE IF EXISTS `log_send_items`;

CREATE TABLE `log_send_items` (
  `log_id` int(11) NOT NULL COMMENT 'sys_record_share_info表的log_id',
  `activity_id` int(11) NOT NULL COMMENT '活动号',
  `passport_name` varchar(100) NOT NULL COMMENT '账号',
  `game_id` int(11) NOT NULL COMMENT '游戏ID',
  `gateway_id` int(11) NOT NULL COMMENT '区服ID',
  `item_code` varchar(200) NOT NULL COMMENT '物品代码',
  `item_num` int(11) NOT NULL COMMENT '物品数量',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `role_id` int(11) DEFAULT '0' COMMENT '角色ID',
  `result` int(11) DEFAULT '0' COMMENT '发送返回值',
  `success_time` timestamp NULL DEFAULT NULL COMMENT '获取返回成功时间',
  PRIMARY KEY (`log_id`,`activity_id`,`passport_name`,`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_click_share_info` */

DROP TABLE IF EXISTS `sys_click_share_info`;

CREATE TABLE `sys_click_share_info` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `uniquly_id` varchar(200) NOT NULL COMMENT '唯一标识',
  `passport_name` varchar(100) NOT NULL,
  `activity_id` int(11) NOT NULL COMMENT '活动号',
  `game_id` int(11) NOT NULL COMMENT '游戏ID',
  `role_id` int(11) DEFAULT '0' COMMENT '角色ID',
  `gateway_id` int(11) NOT NULL COMMENT '区服ID',
  `click_num` int(11) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次点击时间',
  PRIMARY KEY (`log_id`),
  KEY `log_id` (`log_id`),
  KEY `idx_uniquly_id` (`uniquly_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_config_condition` */

DROP TABLE IF EXISTS `sys_config_condition`;

CREATE TABLE `sys_config_condition` (
  `property_key` varchar(200) NOT NULL COMMENT '属性key',
  `property_value` varchar(1000) NOT NULL COMMENT '属性值',
  `property_status` int(11) NOT NULL DEFAULT '1' COMMENT '属性值默认为1整除,0停用',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`property_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_item_config` */

DROP TABLE IF EXISTS `sys_item_config`;

CREATE TABLE `sys_item_config` (
  `activity_id` int(11) NOT NULL COMMENT '活动号',
  `item_code` varchar(200) NOT NULL COMMENT '物品代码',
  `item_num` int(11) NOT NULL COMMENT '物品数据量',
  `game_id` int(11) NOT NULL COMMENT '游戏ID',
  `item_status` int(11) DEFAULT '1' COMMENT '1正常，0冻结配置',
  PRIMARY KEY (`activity_id`,`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_share_user_info` */

DROP TABLE IF EXISTS `sys_share_user_info`;

CREATE TABLE `sys_share_user_info` (
  `uniquly_id` varchar(200) NOT NULL COMMENT '分享用户唯一标识',
  `passport_name` varchar(200) NOT NULL COMMENT '分享用户的游戏账号',
  `game_id` int(11) NOT NULL COMMENT '游戏ID',
  `gateway_id` int(11) NOT NULL COMMENT '区服id',
  `activity_id` int(11) NOT NULL DEFAULT '0' COMMENT '活动ID',
  `role_id` int(11) DEFAULT '0' COMMENT '角色ID',
  `record_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`uniquly_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
