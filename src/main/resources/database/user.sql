/*
 Navicat Premium Data Transfer

 Source Server         : mypetstore
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : health_analytics_db

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 08/07/2025 20:30:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'user' COMMENT '角色，如admin, researcher, analyst, auditor, user, guest',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'testuser', '$2a$10$uX9RYgHgnjpP9cYSkRrThu3OY54KeC.k7TPdNpaAAjLZ4PIu52cMm', 'test@example.com', '13800138000', '北京市海淀区', 'USER');
INSERT INTO `user` VALUES (2, 'admin_user', '$2a$10$LtsHln81MKJgUvvMvz22GeBxe5Auzn3yomS0KrjcJ/Xvn.PRASYbu', NULL, NULL, NULL, 'ADMIN');
INSERT INTO `user` VALUES (3, 'researcher', '$2a$10$IFWCdLFgB75Eqdq4XBRAoe0xsVQubHAWp5ZIpUlRWKMFuvIjJR0SS', NULL, NULL, NULL, 'RESEARCHER');
INSERT INTO `user` VALUES (4, 'normal_user', '$2a$10$kpnwc1cU1qQroNYXu6asmemKtjAoONdruwM3iJ34L3.UyJbyTdHRu', NULL, NULL, NULL, 'USER');

SET FOREIGN_KEY_CHECKS = 1;
