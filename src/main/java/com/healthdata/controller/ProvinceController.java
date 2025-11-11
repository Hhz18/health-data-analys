package com.healthdata.controller;

import com.healthdata.VO.ProvinceLatestVO;
import com.healthdata.entity.Province;
import com.healthdata.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/api/provinces")
@CrossOrigin
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping
    public List<Province> getProvinces() {
        return provinceService.getProvinces();
    }

    @GetMapping("/{provinceId}/latest")
    public ProvinceLatestVO getLatestByProvinceId(@PathVariable Integer provinceId) {
        return provinceService.getLatestByProvinceId(provinceId);
    }

    @GetMapping("/latest")
    public List<ProvinceLatestVO> getAllLatest() {
        return provinceService.getAllLatest();
    }
}