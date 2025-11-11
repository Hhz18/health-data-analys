package com.healthdata.service;

import com.healthdata.VO.ServiceYearDataVO;
import com.healthdata.entity.Serving;

import java.util.List;

public interface ServingService {
    // 获取省份服务最新数据
    Serving getLatestByProvinceId(Integer provinceId);

    // 获取省份服务历年数据
    List<ServiceYearDataVO> getYearsDataByProvinceId(Integer provinceId);
}