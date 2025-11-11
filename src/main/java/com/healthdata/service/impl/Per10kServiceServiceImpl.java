package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.VO.ServicePer10kVO;
import com.healthdata.entity.Per10kService;
import com.healthdata.mappers.Per10kServiceMapper;
import com.healthdata.service.Per10kServiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Per10kServiceServiceImpl extends ServiceImpl<Per10kServiceMapper, Per10kService> implements Per10kServiceService {

    @Override
    public List<ServicePer10kVO> getOutpatientVisitsPer10kByProvinceId(Integer provinceId) {
        QueryWrapper<Per10kService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", provinceId)
                .orderByAsc("year");

        return list(queryWrapper).stream()
                .map(entity -> {
                    ServicePer10kVO vo = new ServicePer10kVO();
                    vo.setYear(entity.getYear());
                    vo.setTotal(entity.getOutpatientVisitsPer10k());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ServicePer10kVO> getInpatientAdmissionsPer10kByProvinceId(Integer provinceId) {
        QueryWrapper<Per10kService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", provinceId)
                .orderByAsc("year");

        return list(queryWrapper).stream()
                .map(entity -> {
                    ServicePer10kVO vo = new ServicePer10kVO();
                    vo.setYear(entity.getYear());
                    vo.setTotal(entity.getInpatientAdmissionsPer10k());
                    return vo;
                })
                .collect(Collectors.toList());
    }
}