package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.VO.PersonnelPer10kVO;
import com.healthdata.entity.Per10kPersonnel;
import com.healthdata.mappers.Per10kPersonnelMapper;
import com.healthdata.service.Per10kPersonnelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Per10kPersonnelServiceImpl extends ServiceImpl<Per10kPersonnelMapper, Per10kPersonnel> implements Per10kPersonnelService {

    @Override
    public List<PersonnelPer10kVO> getPersonnelPer10kByProvinceId(Integer provinceId) {
        QueryWrapper<Per10kPersonnel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", provinceId)
                .orderByAsc("year");

        return list(queryWrapper).stream()
                .map(entity -> {
                    PersonnelPer10kVO vo = new PersonnelPer10kVO();
                    vo.setYear(entity.getYear());
                    vo.setTotal(entity.getPer10kPersonnel()); // 直接映射字段
                    return vo;
                })
                .collect(Collectors.toList());
    }
}