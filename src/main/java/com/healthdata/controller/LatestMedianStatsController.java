package com.healthdata.controller;

import com.healthdata.VO.CommonResponse;
import com.healthdata.VO.LatestMedianStatsVO;
import com.healthdata.service.LatestYearMedianStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats/latest")
@CrossOrigin
public class LatestMedianStatsController {

    @Autowired
    private LatestYearMedianStatsService latestYearMedianStatsService;

    @GetMapping("/median")
    public CommonResponse<LatestMedianStatsVO> getLatestMedianStats() {
        LatestMedianStatsVO statsVO = latestYearMedianStatsService.getLatestMedianStats();
        return CommonResponse.success(statsVO, "查询成功");
    }
}