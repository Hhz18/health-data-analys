package com.healthdata.service;

import com.healthdata.VO.ServicePer10kVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthdata.entity.Per10kService;

import java.util.List;

public interface Per10kServiceService extends IService<Per10kService> {
    /**
     * 获取指定省份的历年每万人门诊诊疗人次
     */
    List<ServicePer10kVO> getOutpatientVisitsPer10kByProvinceId(Integer provinceId);

    /**
     * 获取指定省份的历年每万人住院人数
     */
    List<ServicePer10kVO> getInpatientAdmissionsPer10kByProvinceId(Integer provinceId);
}