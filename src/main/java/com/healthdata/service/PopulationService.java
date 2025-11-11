package com.healthdata.service;

import com.healthdata.VO.AgeDistributionVO;
import com.healthdata.VO.MarketShareVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Population;

import java.util.List;

public interface PopulationService {
    // 获取省份人口最新数据
    Population getLatestByProvinceId(Integer provinceId);

    // 获取省份人口历年数据
    List<YearDataVO> getYearsDataByProvinceId(Integer provinceId);

    // 根据省份ID获取最新年份的年龄分布数据
    List<AgeDistributionVO> getLatestAgeDistribution(Integer provinceId);

    List<MarketShareVO> getLatestGenderData(Integer provinceId);
}