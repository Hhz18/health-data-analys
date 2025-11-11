package com.healthdata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
@CrossOrigin
public class AnalyizeController {

   // 1. 数据汇总
//   @GetMapping("/statistics")
//   public ResponseEntity<?> getStatistics(
//           @RequestParam(value = "province_id", required = false) Integer provinceId,
//           @RequestParam(value = "year", required = false) Integer year) {
//      // TODO: 调用service获取数据
//      return ResponseEntity.ok(/*汇总数据*/);
//   }
//
//   // 2. 省份年度趋势
//   @GetMapping("/province/{provinceId}/trends")
//   public ResponseEntity<?> getProvinceTrends(
//           @PathVariable("provinceId") Integer provinceId,
//           @RequestParam(value = "start_year", required = false) Integer startYear,
//           @RequestParam(value = "end_year", required = false) Integer endYear) {
//      // TODO: 调用service获取数据
//      return ResponseEntity.ok(/*趋势数据*/);
//   }
//
//   // 3. 省份健康评估
//   @GetMapping("/province/{provinceId}/assessment")
//   public ResponseEntity<?> getProvinceAssessment(
//           @PathVariable("provinceId") Integer provinceId,
//           @RequestParam(value = "year", required = false) Integer year) {
//      // TODO: 调用service获取数据
//      return ResponseEntity.ok(/*评估数据*/);
//   }
//
//   // 4. 省份服务利用分析
//   @GetMapping("/province/{provinceId}/service-analysis")
//   public ResponseEntity<?> getProvinceServiceAnalysis(
//           @PathVariable("provinceId") Integer provinceId,
//           @RequestParam(value = "year", required = false) Integer year) {
//      // TODO: 调用service获取数据
//      return ResponseEntity.ok(/*服务利用数据*/);
//   }
//
//   // 5. 省份对比
//   @GetMapping("/comparison")
//   public ResponseEntity<?> getComparison(
//           @RequestParam("province_ids") List<Integer> provinceIds,
//           @RequestParam(value = "start_year", required = false) Integer startYear,
//           @RequestParam(value = "end_year", required = false) Integer endYear) {
//      // TODO: 调用service获取数据
//      return ResponseEntity.ok(/*对比数据*/);
//   }
}
