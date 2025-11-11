package com.healthdata.service;

import com.healthdata.VO.NationLatestVO;
import com.healthdata.VO.BaseStatVO;
import com.healthdata.VO.ServiceYearDataVO;
import com.healthdata.VO.YearDataVO;

import java.util.List;

public interface NationService {
    // 获取全国最新一年数据
    NationLatestVO getLatest();

    // 各指标历年数据
    List<YearDataVO> getPopulationYearsData();
    List<YearDataVO> getInstitutionYearsData();
    List<YearDataVO> getPersonnelYearsData();
    List<YearDataVO> getBedYearsData();
    List<ServiceYearDataVO> getServiceYearsData();
    List<YearDataVO> getCostYearsData();
}