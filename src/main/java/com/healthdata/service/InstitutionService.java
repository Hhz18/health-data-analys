package com.healthdata.service;

import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Institution;

import java.util.List;

public interface InstitutionService {
    // 获取省份机构最新数据
    Institution getLatestByProvinceId(Integer provinceId);

    // 获取省份机构历年数据
    List<YearDataVO> getYearsDataByProvinceId(Integer provinceId);
}