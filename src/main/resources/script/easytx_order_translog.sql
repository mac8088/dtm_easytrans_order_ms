# EMS MySQL Manager Pro 3.4.0.1
# ---------------------------------------
# Host     : localhost
# Port     : 3306
# Database : easytx_order_translog


SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `easytx_order_translog`;

CREATE DATABASE `easytx_order_translog`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `easytx_order_translog`;

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

#
# Structure for the `trans_log_unfinished` table : 
#

DROP TABLE IF EXISTS `trans_log_unfinished`;

CREATE TABLE `trans_log_unfinished` (
  `trans_log_id` binary(12) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`trans_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
