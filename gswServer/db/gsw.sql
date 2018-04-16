/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50132
Source Host           : localhost:3306
Source Database       : gsw

Target Server Type    : MYSQL
Target Server Version : 50132
File Encoding         : 65001

Date: 2018-03-04 11:48:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `ID` int(11) NOT NULL,
  `LOGINNAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `LOGINPSW` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USERNAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USERTYPE` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `CREATETIME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('4', 'admin', '123456', null, null, '2015-05-03 15:47:13');

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `TYPENAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `TYPEID` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(900) COLLATE utf8_bin DEFAULT NULL,
  `STATE` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `IMGPATH` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `FILEPATH` varchar(900) COLLATE utf8_bin DEFAULT NULL,
  `WEBPATH` varchar(900) COLLATE utf8_bin DEFAULT NULL,
  `CREATEUSER` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `CREATEUSERID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CREATETIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ISDEL` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('85', '美女与野兽', null, '19', '发音训练', null, '1454528078263.jpg', '1454528089599.mp3', null, null, null, '2018-02-04 03:34:59', '0');
INSERT INTO `goods` VALUES ('86', '我那个个', null, '20', '荣誉', null, '1454528213804.jpg', '1454528233310.mp3', null, null, '35', '2018-02-04 03:37:25', '0');

-- ----------------------------
-- Table structure for `goodstype`
-- ----------------------------
DROP TABLE IF EXISTS `goodstype`;
CREATE TABLE `goodstype` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPENAME` varchar(100) DEFAULT NULL,
  `CREATEUSER` varchar(100) DEFAULT NULL,
  `CREATETIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ISDEL` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodstype
-- ----------------------------
INSERT INTO `goodstype` VALUES ('19', '发音训练', null, '2018-03-04 11:36:23', '0');
INSERT INTO `goodstype` VALUES ('20', '听力训练', null, '2018-03-04 11:36:30', '0');

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(900) DEFAULT NULL,
  `imgpath` varchar(100) DEFAULT NULL,
  `createusername` varchar(100) DEFAULT NULL,
  `createuserid` int(11) DEFAULT NULL,
  `createtime` varchar(100) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `replaycontent` varchar(900) DEFAULT NULL,
  `replayusername` varchar(100) DEFAULT NULL,
  `replaytime` varchar(100) DEFAULT NULL,
  `replayuserid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('2', '做的很好', '做的很好，继续女里', null, 'zhangfei', '35', '2018-02-04 03:36:27', '已解答', '谢谢，我们会继续努力', null, '2018-02-04 03:38:53', null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOGINNAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `LOGINPSW` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `USERNAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `JOB` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `SEX` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `QQ` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ADDRESS` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `TEL` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `EXAMSTATE` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `CREATETIME` varchar(50) COLLATE utf8_bin DEFAULT 'CURRENT_TIMESTAMP',
  `ISDEL` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('35', 'zhangfei', '123456', '张飞', null, null, null, 'qq@123', null, '13369996755', null, '2018-02-03 14:33:53', '0');
