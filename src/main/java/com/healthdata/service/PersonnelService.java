package com.healthdata.service;

import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Personnel;

import java.util.List;
import java.util.Map;

public interface PersonnelService {
    // 获取省份人员最新数据
    Personnel getLatestByProvinceId(Integer provinceId);

    // 获取省份人员历年数据
    List<YearDataVO> getYearsDataByProvinceId(Integer provinceId);

    List<Map<String, Object>> getLatestPersonnelComponent(Integer provinceId);
}