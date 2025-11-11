package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Institution;
import com.healthdata.mappers.InstitutionMapper;
import com.healthdata.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionMapper institutionMapper;

    @Override
    public Institution getLatestByProvinceId(Integer provinceId) {
        List<Institution> institutions = institutionMapper.selectList(
                new QueryWrapper<Institution>().eq("province_id", provinceId)
        );
        if (institutions.isEmpty()) return null;

        Integer latestYear = institutions.stream()
                .map(Institution::getYear)
                .max(Integer::compare)
                .orElse(null);
        if (latestYear == null) return null;

        return institutionMapper.selectOne(
                new QueryWrapper<Institution>()
                        .eq("province_id", provinceId)
                        .eq("year", latestYear)
        );
    }

    @Override
    public List<YearDataVO> getYearsDataByProvinceId(Integer provinceId) {
        return institutionMapper.selectList(
                        new QueryWrapper<Institution>().eq("province_id", provinceId)
                ).stream()
                .map(institution -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(institution.getYear());
                    vo.setTotal(institution.getTotalInstitutions());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }
}