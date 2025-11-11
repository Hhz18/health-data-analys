package com.healthdata.controller;

import com.healthdata.VO.NationLatestVO;
import com.healthdata.VO.ServiceYearDataVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/national")
@CrossOrigin // 允许跨域
public class NationController {

    @Autowired
    private NationService nationService;

    @GetMapping("/latest")
    public NationLatestVO getLatest() {
        return nationService.getLatest();
    }

    @GetMapping("/population/years")
    public List<YearDataVO> getPopulationYearsData() {
        return nationService.getPopulationYearsData();
    }

    @GetMapping("/institution/years")
    public List<YearDataVO> getInstitutionYearsData() {
        return nationService.getInstitutionYearsData();
    }

    @GetMapping("/personnel/years")
    public List<YearDataVO> getPersonnelYearsData() {
        return nationService.getPersonnelYearsData();
    }

    @GetMapping("/bed/years")
    public List<YearDataVO> getBedYearsData() {
        return nationService.getBedYearsData();
    }

    @GetMapping("/service/years")
    public List<ServiceYearDataVO> getServiceYearsData() {
        return nationService.getServiceYearsData();
    }

    @GetMapping("/cost/years")
    public List<YearDataVO> getCostYearsData() {
        return nationService.getCostYearsData();
    }
}