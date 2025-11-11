package com.healthdata.controller;

import com.healthdata.VO.PersonnelPer10kVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Personnel;
import com.healthdata.service.Per10kPersonnelService;
import com.healthdata.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/provinces/{provinceId}/personnel")
@CrossOrigin
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @GetMapping("/latest")
    public Personnel getLatest(@PathVariable Integer provinceId) {
        return personnelService.getLatestByProvinceId(provinceId);
    }

    @GetMapping("/years")
    public List<YearDataVO> getYearsData(@PathVariable Integer provinceId) {
        return personnelService.getYearsDataByProvinceId(provinceId);
    }
    @Autowired
    private Per10kPersonnelService per10kPersonnelService;

    @GetMapping("/per10k")
    public List<PersonnelPer10kVO> getPersonnelPer10k(@PathVariable Integer provinceId) {
        return per10kPersonnelService.getPersonnelPer10kByProvinceId(provinceId);
    }


    @GetMapping("/component/latest")
    public List<Map<String, Object>> getLatestPersonnelComponent(@PathVariable Integer provinceId) {
        return personnelService.getLatestPersonnelComponent(provinceId);
    }
}