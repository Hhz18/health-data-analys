package com.healthdata.service;

import com.healthdata.VO.ProvinceLatestVO;
import com.healthdata.entity.Province;

import java.util.List;

public interface ProvinceService {
    // 获取所有省份
    List<Province> getProvinces();

    // 获取省份最新数据
    ProvinceLatestVO getLatestByProvinceId(Integer provinceId);

    // 获取所有省份的最新数据
    List<ProvinceLatestVO> getAllLatest();
}