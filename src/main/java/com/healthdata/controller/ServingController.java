package com.healthdata.controller;

import com.healthdata.VO.ServicePer10kVO;
import com.healthdata.VO.ServiceYearDataVO;
import com.healthdata.entity.Serving;
import com.healthdata.service.Per10kServiceService;
import com.healthdata.service.ServingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provinces/{provinceId}/service")
@CrossOrigin
public class ServingController {

    @Autowired
    private ServingService servingService;

    @GetMapping("/latest")
    public Serving getLatest(@PathVariable Integer provinceId) {
        return servingService.getLatestByProvinceId(provinceId);
    }

    @GetMapping("/years")
    public List<ServiceYearDataVO> getYearsData(@PathVariable Integer provinceId) {
        return servingService.getYearsDataByProvinceId(provinceId);
    }

    @Autowired
    private Per10kServiceService per10kServiceService;

    /**
     * 获取指定省份的历年每万人门诊诊疗人次
     */
    @GetMapping("/outpatient/per10k")
    public List<ServicePer10kVO> getOutpatientVisitsPer10k(@PathVariable Integer provinceId) {
        return per10kServiceService.getOutpatientVisitsPer10kByProvinceId(provinceId);
    }

    /**
     * 获取指定省份的历年每万人住院人数
     */
    @GetMapping("/inpatient/per10k")
    public List<ServicePer10kVO> getInpatientAdmissionsPer10k(@PathVariable Integer provinceId) {
        return per10kServiceService.getInpatientAdmissionsPer10kByProvinceId(provinceId);
    }
}