package com.healthdata.service;

import com.healthdata.VO.CostPerCapitaVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthdata.entity.PerCapitaCost;

import java.util.List;

public interface PerCapitaCostService extends IService<PerCapitaCost> {
    /**
     * 获取指定省份的历年人均医疗费用
     */
    List<CostPerCapitaVO> getCostPerCapitaByProvinceId(Integer provinceId);
}