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

 Date: 08/07/2025 21:18:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for latest_year_median_stats
-- ----------------------------
DROP TABLE IF EXISTS `latest_year_median_stats`;
CREATE TABLE `latest_year_median_stats`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `year` year NOT NULL COMMENT '统计年份',
  `median_total_beds` decimal(10, 2) NULL DEFAULT NULL COMMENT '床位总数中位数',
  `median_total_expenditure` decimal(12, 2) NULL DEFAULT NULL COMMENT '医疗总费用中位数(亿元)',
  `median_total_institutions` decimal(10, 2) NULL DEFAULT NULL COMMENT '医疗机构总数中位数',
  `median_total_personnel` decimal(10, 2) NULL DEFAULT NULL COMMENT '医疗人员总数中位数',
  `median_outpatient_visits` decimal(15, 2) NULL DEFAULT NULL COMMENT '门诊诊疗人次中位数（万人）',
  `median_inpatient_admissions` decimal(15, 2) NULL DEFAULT NULL COMMENT '入院人数中位数（万人）',
  `province_id_total_beds_median` int NULL DEFAULT NULL COMMENT '床位总数中位数对应的省份ID',
  `province_id_total_expenditure_median` int NULL DEFAULT NULL COMMENT '医疗总费用中位数对应的省份ID',
  `province_id_total_institutions_median` int NULL DEFAULT NULL COMMENT '医疗机构总数中位数对应的省份ID',
  `province_id_total_personnel_median` int NULL DEFAULT NULL COMMENT '医疗人员总数中位数对应的省份ID',
  `province_id_outpatient_visits_median` int NULL DEFAULT NULL COMMENT '门诊诊疗人次中位数对应的省份ID',
  `province_id_inpatient_admissions_median` int NULL DEFAULT NULL COMMENT '入院人数中位数对应的省份ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_year`(`year` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of latest_year_median_stats
-- ----------------------------
INSERT INTO `latest_year_median_stats` VALUES (2, 2020, 220000.00, 1250.70, 11400.00, 456000.00, 8617.00, 456.00, 20, 4, 14, 14, 29, 4);

SET FOREIGN_KEY_CHECKS = 1;
