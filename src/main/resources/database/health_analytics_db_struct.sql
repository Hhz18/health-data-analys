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

 Date: 08/07/2025 20:27:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bed_stat
-- ----------------------------
DROP TABLE IF EXISTS `bed_stat`;
CREATE TABLE `bed_stat`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `province_id` int NULL DEFAULT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `total_beds` int NOT NULL COMMENT '床位总数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_province_year`(`province_id` ASC, `year` ASC) USING BTREE,
  CONSTRAINT `bed_stat_ibfk_1` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 514 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cost_stat
-- ----------------------------
DROP TABLE IF EXISTS `cost_stat`;
CREATE TABLE `cost_stat`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `province_id` int NULL DEFAULT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `total_expenditure` decimal(12, 2) NULL DEFAULT NULL COMMENT '总费用(亿元)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_province_year`(`province_id` ASC, `year` ASC) USING BTREE,
  CONSTRAINT `cost_stat_ibfk_1` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 886 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for institution_stat
-- ----------------------------
DROP TABLE IF EXISTS `institution_stat`;
CREATE TABLE `institution_stat`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `province_id` int NULL DEFAULT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `total_institutions` int NOT NULL COMMENT '医疗机构总数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_province_year`(`province_id` ASC, `year` ASC) USING BTREE,
  CONSTRAINT `institution_stat_ibfk_1` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 457 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for national_stat
-- ----------------------------
DROP TABLE IF EXISTS `national_stat`;
CREATE TABLE `national_stat`  (
  `year` year NOT NULL COMMENT '统计年份',
  `total_population` decimal(15, 2) NOT NULL COMMENT '人口总数（万人）',
  `total_beds` int NOT NULL COMMENT '床位总数',
  `total_expenditure` decimal(18, 2) NOT NULL COMMENT '医疗总费用（亿元）',
  `total_institutions` int NOT NULL COMMENT '医疗机构总数',
  `total_personnel` int NOT NULL COMMENT '医疗人员总数',
  `outpatient_visits` bigint NOT NULL COMMENT '门诊诊疗人次（万人）',
  `inpatient_admissions` bigint NOT NULL COMMENT '入院人数（万人）',
  PRIMARY KEY (`year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '全国医疗统计数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for per_10k_beds
-- ----------------------------
DROP TABLE IF EXISTS `per_10k_beds`;
CREATE TABLE `per_10k_beds`  (
  `province_id` int NOT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `per_10k_beds` decimal(10, 4) NULL DEFAULT NULL COMMENT '人均床位数',
  PRIMARY KEY (`province_id`, `year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for per_10k_institutions
-- ----------------------------
DROP TABLE IF EXISTS `per_10k_institutions`;
CREATE TABLE `per_10k_institutions`  (
  `province_id` int NOT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `per_10k_institutions` decimal(10, 4) NULL DEFAULT NULL COMMENT '每万人医疗机构数',
  PRIMARY KEY (`province_id`, `year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for per_10k_personnel
-- ----------------------------
DROP TABLE IF EXISTS `per_10k_personnel`;
CREATE TABLE `per_10k_personnel`  (
  `province_id` int NOT NULL COMMENT '省份ID（0表示全国）',
  `year` year NOT NULL COMMENT '统计年份',
  `per_10k_personnel` decimal(10, 4) NULL DEFAULT NULL COMMENT '每万人医疗人员数',
  PRIMARY KEY (`province_id`, `year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for per_10k_service
-- ----------------------------
DROP TABLE IF EXISTS `per_10k_service`;
CREATE TABLE `per_10k_service`  (
  `province_id` int NOT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `per_10k_outpatient_visits` decimal(10, 4) NULL DEFAULT NULL COMMENT '每万人门诊诊疗人次',
  `per_10k_inpatient_admissions` decimal(10, 4) NULL DEFAULT NULL COMMENT '每万人入院次数',
  PRIMARY KEY (`province_id`, `year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for per_capita_cost
-- ----------------------------
DROP TABLE IF EXISTS `per_capita_cost`;
CREATE TABLE `per_capita_cost`  (
  `province_id` int NOT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `per_capita_cost` decimal(10, 4) NULL DEFAULT NULL COMMENT '人均费用(元)',
  PRIMARY KEY (`province_id`, `year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `resource` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源标识（如patient_info、research_data）',
  `action` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型（view、edit、delete）',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_resource_action`(`resource` ASC, `action` ASC) USING BTREE COMMENT '资源+操作唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for personnel_stat
-- ----------------------------
DROP TABLE IF EXISTS `personnel_stat`;
CREATE TABLE `personnel_stat`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `province_id` int NULL DEFAULT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `total_personnel` int NOT NULL COMMENT '医疗人员总数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_province_year`(`province_id` ASC, `year` ASC) USING BTREE,
  CONSTRAINT `personnel_stat_ibfk_1` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 459 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for population_stat
-- ----------------------------
DROP TABLE IF EXISTS `population_stat`;
CREATE TABLE `population_stat`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `province_id` int NULL DEFAULT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `total_population` decimal(10, 0) NOT NULL COMMENT '总人口(万人)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_province_year`(`province_id` ASC, `year` ASC) USING BTREE,
  CONSTRAINT `population_stat_ibfk_1` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 482 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`  (
  `province_id` int NOT NULL AUTO_INCREMENT COMMENT '省份ID',
  `province_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份名称',
  PRIMARY KEY (`province_id`) USING BTREE,
  UNIQUE INDEX `province_name`(`province_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for province_health_statistics
-- ----------------------------
DROP TABLE IF EXISTS `province_health_statistics`;
CREATE TABLE `province_health_statistics`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `province_id` int NOT NULL COMMENT '省份ID',
  `province_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份名称',
  `year` year NOT NULL COMMENT '统计年份',
  `total_population` decimal(10, 0) NULL DEFAULT 0 COMMENT '总人口(万人)',
  `total_beds` int NULL DEFAULT 0 COMMENT '床位总数',
  `total_expenditure` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '医疗总费用(亿元)',
  `total_institutions` int NULL DEFAULT 0 COMMENT '医疗机构总数',
  `total_personnel` int NULL DEFAULT 0 COMMENT '医疗人员总数',
  `outpatient_visits` bigint NULL DEFAULT 0 COMMENT '门诊人次(万人)',
  `inpatient_admissions` bigint NULL DEFAULT 0 COMMENT '入院人数(万人)',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_province_year`(`province_id` ASC, `year` ASC) USING BTREE,
  CONSTRAINT `province_health_statistics_ibfk_1` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 512 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '省份健康汇总统计表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色（ADMIN、RESEARCHER等）',
  `permission_id` int NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_permission`(`role` ASC, `permission_id` ASC) USING BTREE COMMENT '角色+权限唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for service_stat
-- ----------------------------
DROP TABLE IF EXISTS `service_stat`;
CREATE TABLE `service_stat`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `province_id` int NULL DEFAULT NULL COMMENT '省份ID',
  `year` year NOT NULL COMMENT '统计年份',
  `outpatient_visits` bigint NULL DEFAULT NULL COMMENT '门诊诊疗人次（万人）',
  `inpatient_admissions` bigint NULL DEFAULT NULL COMMENT '入院人数（万人）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_province_year`(`province_id` ASC, `year` ASC) USING BTREE,
  CONSTRAINT `service_stat_ibfk_1` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 611 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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

SET FOREIGN_KEY_CHECKS = 1;
