package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.VO.CostPerCapitaVO;
import com.healthdata.entity.PerCapitaCost;
import com.healthdata.mappers.PerCapitaCostMapper;
import com.healthdata.service.PerCapitaCostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerCapitaCostServiceImpl extends ServiceImpl<PerCapitaCostMapper, PerCapitaCost> implements PerCapitaCostService {

    @Override
    public List<CostPerCapitaVO> getCostPerCapitaByProvinceId(Integer provinceId) {
        QueryWrapper<PerCapitaCost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", provinceId)
                .orderByAsc("year");

        return list(queryWrapper).stream()
                .map(entity -> {
                    CostPerCapitaVO vo = new CostPerCapitaVO();
                    vo.setYear(entity.getYear());
                    vo.setTotal(entity.getPerCapitaCost());
                    return vo;
                })
                .collect(Collectors.toList());
    }
}