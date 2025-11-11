package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthdata.VO.ServiceYearDataVO;
import com.healthdata.entity.Serving;
import com.healthdata.mappers.ServingMapper;
import com.healthdata.service.ServingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServingServiceImpl implements ServingService {

    @Autowired
    private ServingMapper servingMapper;

    @Override
    public Serving getLatestByProvinceId(Integer provinceId) {
        List<Serving> servings = servingMapper.selectList(
                new QueryWrapper<Serving>().eq("province_id", provinceId)
        );
        if (servings.isEmpty()) return null;

        Integer latestYear = servings.stream()
                .map(Serving::getYear)
                .max(Integer::compare)
                .orElse(null);
        if (latestYear == null) return null;

        return servingMapper.selectOne(
                new QueryWrapper<Serving>()
                        .eq("province_id", provinceId)
                        .eq("year", latestYear)
        );
    }

    @Override
    public List<ServiceYearDataVO> getYearsDataByProvinceId(Integer provinceId) {
        return servingMapper.selectList(
                        new QueryWrapper<Serving>().eq("province_id", provinceId)
                ).stream()
                .map(serving -> {
                    ServiceYearDataVO vo = new ServiceYearDataVO();
                    vo.setYear(serving.getYear());
                    vo.setOutpatientVisits(serving.getOutpatientVisits());
                    vo.setInpatientAdmissions(serving.getInpatientAdmissions());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }
}