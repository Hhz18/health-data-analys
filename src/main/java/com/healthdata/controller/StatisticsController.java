package com.healthdata.controller;

import com.healthdata.VO.CommonResponse;
import com.healthdata.VO.ProvinceDataVO;
import com.healthdata.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 获取最新年份的省份统计数据（适配饼状图）
     */
    @GetMapping("/latest-province-data")
    public CommonResponse<List<ProvinceDataVO>> getLatestProvinceData(
            @RequestParam(required = false) List<Integer> provinceIds,
            @RequestParam(required = false) List<String> dataTypes) {

        List<ProvinceDataVO> result = statisticsService.getLatestProvinceData(provinceIds, dataTypes);
        return CommonResponse.success(result, "查询成功");
    }
}