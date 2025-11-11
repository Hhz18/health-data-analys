package com.healthdata.controller;

import com.healthdata.VO.AgeDistributionVO;
import com.healthdata.VO.CommonResponse;
import com.healthdata.VO.MarketShareVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Population;
import com.healthdata.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces/{provinceId}/population")
@CrossOrigin
public class PopulationController {

    @Autowired
    private PopulationService populationService;

    @GetMapping("/latest")
    public Population getLatest(@PathVariable Integer provinceId) {
        return populationService.getLatestByProvinceId(provinceId);
    }

    @GetMapping("/years")
    public List<YearDataVO> getYearsData(@PathVariable Integer provinceId) {
        return populationService.getYearsDataByProvinceId(provinceId);
    }

    /**
     * 获取指定省份最新年份的年龄分布数据
     * 接口路径：/api/population/{provinceId}/age-distribution/latest
     */
    @GetMapping("/age-distribution/latest")
    public CommonResponse<List<AgeDistributionVO>> getLatestAgeDistribution(
            @PathVariable Integer provinceId // 从路径中获取省份ID
    ) {
        List<AgeDistributionVO> result = populationService.getLatestAgeDistribution(provinceId);
        return CommonResponse.success(result, "查询成功");
    }


    /**
     * 获取指定省份最新年份的男女人口数据
     * @param provinceId 省份ID（0表示全国）
     * @return 男女人口分布数据
     */
    @GetMapping("/gender/latest")
    public CommonResponse<List<MarketShareVO>> getLatestGenderData(
            @PathVariable Integer provinceId
    ) {
        List<MarketShareVO> result = populationService.getLatestGenderData(provinceId);
        return CommonResponse.success(result,"查询成功");
    }
}