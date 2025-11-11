package com.healthdata.controller;

import com.healthdata.VO.InstitutionsPer10kVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Institution;
import com.healthdata.service.InstitutionService;
import com.healthdata.service.Per10kInstitutionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provinces/{provinceId}/institution")
@CrossOrigin
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @GetMapping("/latest")
    public Institution getLatest(@PathVariable Integer provinceId) {
        return institutionService.getLatestByProvinceId(provinceId);
    }

    @GetMapping("/years")
    public List<YearDataVO> getYearsData(@PathVariable Integer provinceId) {
        return institutionService.getYearsDataByProvinceId(provinceId);
    }

    @Autowired
    private Per10kInstitutionsService per10kInstitutionsService;

    @GetMapping("/per10k")
    public List<InstitutionsPer10kVO> getInstitutionsPer10k(@PathVariable Integer provinceId) {
        return per10kInstitutionsService.getInstitutionsPer10kByProvinceId(provinceId);
    }
}