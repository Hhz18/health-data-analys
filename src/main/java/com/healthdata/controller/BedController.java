package com.healthdata.controller;

import com.healthdata.VO.BedsPer10kVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Bed;
import com.healthdata.service.BedService;
import com.healthdata.service.Per10kBedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provinces/{provinceId}/bed")
@CrossOrigin
public class BedController {

    @Autowired
    private BedService bedService;

    @Autowired
    private Per10kBedsService per10kBedsService;

    @GetMapping("/latest")
    public Bed getLatest(@PathVariable Integer provinceId) {
        return bedService.getLatestByProvinceId(provinceId);
    }

    @GetMapping("/years")
    public List<YearDataVO> getYearsData(@PathVariable Integer provinceId) {
        return bedService.getYearsDataByProvinceId(provinceId);
    }

    @GetMapping("/{year}")
    public List<Bed> getBedsByYear(@PathVariable Integer provinceId, @PathVariable Integer year) {
        return bedService.getBedsByProvinceAndYear(provinceId, year);
    }
    // 新增：每万人床位数接口，调用 Per10kBedsService
    @GetMapping("/per10k")
    public List<BedsPer10kVO> getBedsPer10k(@PathVariable Integer provinceId) {
        return per10kBedsService.getBedsPer10kByProvinceId(provinceId);
    }
}