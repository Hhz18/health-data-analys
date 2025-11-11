package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Cost;
import com.healthdata.mappers.CostMapper;
import com.healthdata.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostServiceImpl implements CostService {

    @Autowired
    private CostMapper costMapper;

    @Override
    public Cost getLatestByProvinceId(Integer provinceId) {
        List<Cost> costs = costMapper.selectList(
                new QueryWrapper<Cost>().eq("province_id", provinceId)
        );
        if (costs.isEmpty()) return null;

        Integer latestYear = costs.stream()
                .map(Cost::getYear)
                .max(Integer::compare)
                .orElse(null);
        if (latestYear == null) return null;

        return costMapper.selectOne(
                new QueryWrapper<Cost>()
                        .eq("province_id", provinceId)
                        .eq("year", latestYear)
        );
    }

    @Override
    public List<YearDataVO> getYearsDataByProvinceId(Integer provinceId) {
        return costMapper.selectList(
                        new QueryWrapper<Cost>().eq("province_id", provinceId)
                ).stream()
                .map(cost -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(cost.getYear());
                    vo.setTotal(cost.getTotalExpenditure());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }
}