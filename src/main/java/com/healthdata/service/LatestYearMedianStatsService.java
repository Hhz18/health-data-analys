package com.healthdata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthdata.VO.LatestMedianStatsVO;
import com.healthdata.entity.LatestYearMedianStats;

public interface LatestYearMedianStatsService extends IService<LatestYearMedianStats> {
    LatestMedianStatsVO getLatestMedianStats();
}