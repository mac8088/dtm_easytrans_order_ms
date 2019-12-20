# EMS MySQL Manager Pro 3.4.0.1
# ---------------------------------------
# Host     : localhost
# Port     : 3306
# Database : easytx_common_translog


SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `easytx_common_translog`;

CREATE DATABASE `easytx_common_translog`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `easytx_common_translog`;

#
# Structure for the `election` table : 
#

DROP TABLE IF EXISTS `election`;

CREATE TABLE `election` (
  `app_id` varchar(64) NOT NULL COMMENT 'AppId',
  `instance_id` int(11) NOT NULL COMMENT '实例id，递增',
  `heart_beat_time` datetime NOT NULL COMMENT '上次master发送心跳的时间',
  `instance_name` varchar(255) DEFAULT NULL COMMENT '当前实例的名称',
  PRIMARY KEY (`app_id`,`instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `str_codec` table : 
#

DROP TABLE IF EXISTS `str_codec`;

CREATE TABLE `str_codec` (
  `key_int` int(11) NOT NULL,
  `type_str` varchar(45) NOT NULL,
  `value_str` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`key_int`),
  UNIQUE KEY `str_type_UNIQUE` (`type_str`,`value_str`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `trans_log_detail` table : 
#

DROP TABLE IF EXISTS `trans_log_detail`;

CREATE TABLE `trans_log_detail` (
  `log_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `trans_log_id` binary(12) NOT NULL,
  `log_detail` blob,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`log_detail_id`),
  KEY `app_id` (`trans_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for the `trans_log_detail` table  (LIMIT 0,500)
#

#
# Structure for the `trans_log_unfinished` table : 
#

DROP TABLE IF EXISTS `trans_log_unfinished`;

CREATE TABLE `trans_log_unfinished` (
  `trans_log_id` binary(12) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`trans_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
