package com.healthdata.service;

import com.healthdata.VO.BedsPer10kVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Bed;

import java.util.List;

public interface BedService {
    // 获取省份床位最新数据
    Bed getLatestByProvinceId(Integer provinceId);

    // 获取省份床位历年数据
    List<YearDataVO> getYearsDataByProvinceId(Integer provinceId);

    // 获取省份某一年的床位数据
    List<Bed> getBedsByProvinceAndYear(Integer provinceId, Integer year);
//
//    // 获取指定省份历年每万人床位数
//    List<BedsPer10kVO> getBedsPer10kByProvinceId(Integer provinceId);
}