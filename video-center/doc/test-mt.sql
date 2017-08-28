/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : test-mt

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2017-08-21 18:07:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `gold_count` int(11) DEFAULT NULL COMMENT '金币数量',
  `is_delete` tinyint(4) DEFAULT NULL COMMENT '是否删除，0否，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('12', '孟涛', '12', null, null, null);

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video` (
  `id` varchar(32) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '视频标题',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `play_count` int(11) DEFAULT NULL COMMENT '累计播放 次数',
  `crt_by_user_id` varchar(32) DEFAULT NULL COMMENT '创建者ID',
  `crt_by_user_name` varchar(255) DEFAULT NULL COMMENT '创建者昵称',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_delete` tinyint(4) DEFAULT NULL COMMENT '是否删除，0否，1是'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_video
-- ----------------------------
