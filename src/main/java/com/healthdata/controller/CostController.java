package com.healthdata.controller;

import com.healthdata.VO.CostPerCapitaVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Cost;
import com.healthdata.service.CostService;
import com.healthdata.service.PerCapitaCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provinces/{provinceId}/cost")
@CrossOrigin
public class CostController {

    @Autowired
    private CostService costService;

    @GetMapping("/latest")
    public Cost getLatest(@PathVariable Integer provinceId) {
        return costService.getLatestByProvinceId(provinceId);
    }

    @GetMapping("/years")
    public List<YearDataVO> getYearsData(@PathVariable Integer provinceId) {
        return costService.getYearsDataByProvinceId(provinceId);
    }

    @Autowired
    private PerCapitaCostService perCapitaCostService;

    @GetMapping("/percapita")
    public List<CostPerCapitaVO> getCostPerCapita(@PathVariable Integer provinceId) {
        return perCapitaCostService.getCostPerCapitaByProvinceId(provinceId);
    }
}