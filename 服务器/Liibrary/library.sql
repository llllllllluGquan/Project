/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : library

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2016-09-12 19:57:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `administrator`
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `id` int(100) NOT NULL,
  `seat_principal` varchar(10) DEFAULT NULL,
  `seat_principal_num` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seat_principal` (`seat_principal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('1', '张三', '20131234');
INSERT INTO `administrator` VALUES ('2', '李四', '20121230');
INSERT INTO `administrator` VALUES ('3', '王五', '20131240');
INSERT INTO `administrator` VALUES ('4', '马六', '20101223');

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(255) NOT NULL,
  `bookname` varchar(30) NOT NULL,
  `book_author` varchar(20) NOT NULL,
  `book_public` varchar(30) NOT NULL,
  `book_state` varchar(10) NOT NULL,
  `book_margin` int(255) NOT NULL,
  `book_num` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '大学物理实验', '王永刚', '中国农业出版社', 'yes', '12', 'TP312CC441');
INSERT INTO `book` VALUES ('2', 'Java Web应用开发技术', '张继军 董魏', '机械工业出版社', 'yes', '14', 'TP313CC123');
INSERT INTO `book` VALUES ('3', '数据库及其应用', '肖慎勇 熊平', '清华大学出版社', 'yes', '26', 'TP124BB332');
INSERT INTO `book` VALUES ('4', 'jQuery实战', 'Bear Bilbeault', '人民邮电出版社', 'yes', '36', 'TP652DD781');
INSERT INTO `book` VALUES ('5', '从来没有太晚的开始', '韩京姬(韩)', '长江文艺出版社', 'yes', '47', 'WX978AA123');
INSERT INTO `book` VALUES ('6', '高等数学A上册', '同济大学数学系', '高等教育出版社', 'no', '30', 'JX433AA897');
INSERT INTO `book` VALUES ('7', '高等数学A下册', '同济大学数学系', '高等教育出版社', 'yes', '49', 'JX234AA231');
INSERT INTO `book` VALUES ('8', 'C++面向对象程序设计', '李素若 杜华兵', '中国水利水电出版社', 'yes', '19', 'JX125AA322');
INSERT INTO `book` VALUES ('9', '汇编语言程序设计', '徐建民', '电子工业出版社', 'yes', '32', 'JX097DD323');
INSERT INTO `book` VALUES ('10', '疯狂android讲义', '李刚', '电子工业出版社', 'yes', '53', 'TP787EE124');

-- ----------------------------
-- Table structure for `seat`
-- ----------------------------
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `seatNum` varchar(20) NOT NULL,
  `principal` varchar(10) NOT NULL,
  `state` varchar(5) NOT NULL,
  `position` varchar(30) NOT NULL,
  `occupyNum` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`seatNum`),
  KEY `principas` (`principal`),
  CONSTRAINT `principas` FOREIGN KEY (`principal`) REFERENCES `administrator` (`seat_principal`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seat
-- ----------------------------
INSERT INTO `seat` VALUES ('1', '001', '张三', '1', '二楼南区', '0');
INSERT INTO `seat` VALUES ('2', '002', '张三', '0', '二楼南区', '0');
INSERT INTO `seat` VALUES ('3', '003', '张三', '0', '二楼北区', '0');
INSERT INTO `seat` VALUES ('4', '004', '张三', '1', '二楼中区', '0');
INSERT INTO `seat` VALUES ('5', '005', '张三', '1', '二楼中区', '0');
INSERT INTO `seat` VALUES ('6', '006', '张三', '0', '二楼北区', '0');
INSERT INTO `seat` VALUES ('7', '007', '李四', '1', '三楼北区', '0');
INSERT INTO `seat` VALUES ('8', '008', '李四', '1', '三楼北区', '0');
INSERT INTO `seat` VALUES ('9', '009', '李四', '0', '三楼中区', '0');
INSERT INTO `seat` VALUES ('10', '010', '李四', '1', '三楼中区', '0');
INSERT INTO `seat` VALUES ('11', '011', '李四', '0', '三楼南区', '0');
INSERT INTO `seat` VALUES ('12', '012', '李四', '1', '三楼南区', '0');
INSERT INTO `seat` VALUES ('13', '013', '王五', '1', '四楼南区', '0');
INSERT INTO `seat` VALUES ('14', '014', '王五', '0', '四楼南区', '0');
INSERT INTO `seat` VALUES ('15', '015', '王五', '1', '四楼北区', '0');
INSERT INTO `seat` VALUES ('16', '016', '王五', '0', '四楼中区', '0');
INSERT INTO `seat` VALUES ('17', '017', '王五', '0', '四楼中区', '0');
INSERT INTO `seat` VALUES ('18', '018', '王五', '1', '四楼北区', '0');
INSERT INTO `seat` VALUES ('19', '019', '马六', '0', '五楼南区', '0');
INSERT INTO `seat` VALUES ('20', '020', '马六', '0', '五楼南区', '0');
INSERT INTO `seat` VALUES ('21', '021', '马六', '1', '五楼北区', '0');
INSERT INTO `seat` VALUES ('22', '022', '马六', '0', '五楼北区', '0');
INSERT INTO `seat` VALUES ('23', '023', '马六', '1', '五楼中区', '0');
INSERT INTO `seat` VALUES ('24', '024', '马六', '1', '五楼中区', '0');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `userName` varchar(16) NOT NULL,
  `password` varchar(12) NOT NULL,
  `usertype` varchar(15) NOT NULL,
  `can_borrow_book` int(2) DEFAULT NULL,
  `sex` varchar(2) NOT NULL,
  `age` varchar(3) NOT NULL,
  `department` varchar(15) NOT NULL,
  `grade` varchar(5) NOT NULL,
  `can_book_seat` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '20144833', '20144833', 'stuent', '0', '男', '20', '信息学院', '14级', 'no');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `another_name` varchar(30) NOT NULL,
  `signature` varchar(30) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `sex` varchar(5) NOT NULL,
  `address` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `user` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '20144833', 'Boo', '我是要成为海贼王的男人！！！', '山东 泰安', '男', '山东省潍坊市');

-- ----------------------------
-- Table structure for `user_book`
-- ----------------------------
DROP TABLE IF EXISTS `user_book`;
CREATE TABLE `user_book` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `book_name` varchar(30) NOT NULL,
  `book_num` varchar(20) NOT NULL,
  `date` varchar(30) NOT NULL,
  `end_date` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_book
-- ----------------------------
INSERT INTO `user_book` VALUES ('9', '20144833', '数据库及其应用', 'TP124BB332', '2016-09-03', '2016-12-02');
INSERT INTO `user_book` VALUES ('10', '20144833', '从来没有太晚的开始', 'WX978AA123', '2016-09-03', '2016-12-02');
INSERT INTO `user_book` VALUES ('11', '20144833', 'jQuery实战', 'TP652DD781', '2016-09-10', '2016-12-09');

-- ----------------------------
-- Table structure for `user_seat`
-- ----------------------------
DROP TABLE IF EXISTS `user_seat`;
CREATE TABLE `user_seat` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `user` varchar(30) NOT NULL,
  `curr_seatNum` varchar(30) DEFAULT NULL,
  `curr_position` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_seat
-- ----------------------------
INSERT INTO `user_seat` VALUES ('9', '20144833', '001', '二楼南区');
