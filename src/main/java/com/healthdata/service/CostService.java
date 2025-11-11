package com.healthdata.service;

import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Cost;

import java.util.List;

public interface CostService {
    // 获取省份费用最新数据
    Cost getLatestByProvinceId(Integer provinceId);

    // 获取省份费用历年数据
    List<YearDataVO> getYearsDataByProvinceId(Integer provinceId);
}