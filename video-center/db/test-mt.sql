/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : test-mt

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2017-08-27 19:47:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_token
-- ----------------------------
DROP TABLE IF EXISTS `t_token`;
CREATE TABLE `t_token` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_token
-- ----------------------------
INSERT INTO `t_token` VALUES ('402881bb5e185a69015e185a6a490001', '402881bb5e185a69015e185a69bb0000', '8b8734804fbeb7eabf730bdacd6f9d16', '2017-08-27 19:01:18');
INSERT INTO `t_token` VALUES ('402881bb5e18644b015e186c1ef50003', '402881bb5e18644b015e186c1e710002', 'b4b8fd42d2ce3bff23fd5493290c3886', '2017-08-27 18:02:52');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `gold_count` int(11) DEFAULT NULL COMMENT '金币数量',
  `is_delete` tinyint(4) DEFAULT NULL COMMENT '是否删除，0否，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('402881bb5e185a69015e185a69bb0000', '673365', '13127673365', '120', '0');
INSERT INTO `t_user` VALUES ('402881bb5e18644b015e186c1e710002', '927403', '13839927403', '0', '0');

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video` (
  `id` varchar(32) NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '视频标题',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `play_count` int(11) DEFAULT NULL COMMENT '累计播放 次数',
  `crt_by_user_id` varchar(32) DEFAULT NULL COMMENT '创建者ID',
  `crt_by_user_name` varchar(255) DEFAULT NULL COMMENT '创建者昵称',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `thumbnail_path` varchar(255) DEFAULT NULL,
  `is_delete` tinyint(4) DEFAULT NULL COMMENT '是否删除，0否，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_video
-- ----------------------------
INSERT INTO `t_video` VALUES ('402881bb5e185e64015e185e64830001', '极限挑战粉丝嘉年华', '402881bb5e185e64015e185e64400000.mp4', '1', '402881bb5e185a69015e185a69bb0000', '73365', '2017-08-25 15:49:52', '402881bb5e185e64015e185e64400000.jpg', '0');
INSERT INTO `t_video` VALUES ('402881bb5e18644b015e18644bcd0001', '跆拳道活动', '402881bb5e18644b015e18644bbd0000.mp4', '1', '402881bb5e185a69015e185a69bb0000', '73365', '2017-08-25 15:56:19', '402881bb5e18644b015e18644bbd0000.jpg', '0');
INSERT INTO `t_video` VALUES ('402881bb5e1878ff015e1878ff630001', '小葵花', '402881bb5e1878ff015e1878ff490000.mp4', '9', '402881bb5e185a69015e185a69bb0000', '673365', '2017-08-25 16:18:56', '402881bb5e1878ff015e1878ff490000.jpg', '0');
INSERT INTO `t_video` VALUES ('402881bb5e1878ff015e187c4df20003', '海边海边海边海边海边海边海边海边海边海边', '402881bb5e1878ff015e187c4de10002.mp4', '14', '402881bb5e185a69015e185a69bb0000', '673365', '2017-08-25 16:22:33', '402881bb5e1878ff015e187c4de10002.jpg', '0');
INSERT INTO `t_video` VALUES ('402881bb5e1881be015e1881be6b0001', '环球奇趣馆', '402881bb5e1881be015e1881be550000.mp4', '5', '402881bb5e185a69015e185a69bb0000', '673365', '2017-08-25 16:28:29', '402881bb5e1881be015e1881be550000.jpg', '0');
INSERT INTO `t_video` VALUES ('402881bb5e188506015e1885063c0001', '环球奇趣', '402881bb5e188506015e188506250000.mp4', '75', '402881bb5e185a69015e185a69bb0000', '673365', '2017-08-25 16:32:04', '402881bb5e188506015e188506250000.jpg', '0');
INSERT INTO `t_video` VALUES ('402881bb5e18b2cd015e18b2cd6d0001', '我去玩啦', '402881bb5e18b2cd015e18b2cd1a0000.mp4', '1', '402881bb5e07f76a015e07f76af10000', '673365', '2017-08-25 17:22:04', '402881bb5e18b2cd015e18b2cd1a0000.jpg', '0');

-- ----------------------------
-- Table structure for t_video_collect
-- ----------------------------
DROP TABLE IF EXISTS `t_video_collect`;
CREATE TABLE `t_video_collect` (
  `id` varchar(32) NOT NULL,
  `video_id` varchar(32) DEFAULT NULL,
  `video_title` varchar(255) DEFAULT NULL,
  `video_thumbnail_path` varchar(255) DEFAULT NULL,
  `video_crt_time` datetime DEFAULT NULL,
  `video_file_name` varchar(255) NOT NULL,
  `video_crt_by_user_id` varchar(32) DEFAULT NULL,
  `video_crt_by_user_name` varchar(255) DEFAULT NULL,
  `collect_usr_id` varchar(32) DEFAULT NULL,
  `is_delete` tinyint(4) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_video_collect
-- ----------------------------

-- ----------------------------
-- Table structure for t_view_history
-- ----------------------------
DROP TABLE IF EXISTS `t_view_history`;
CREATE TABLE `t_view_history` (
  `id` varchar(32) NOT NULL,
  `video_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_view_history
-- ----------------------------
