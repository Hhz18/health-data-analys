package com.healthdata.service;

import com.healthdata.VO.ProvinceDataVO;
import java.util.List;

public interface StatisticsService {
    // 新增方法：获取最新年份的省份统计数据
    List<ProvinceDataVO> getLatestProvinceData(List<Integer> provinceIds, List<String> dataTypes);
}