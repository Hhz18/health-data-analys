package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.VO.InstitutionsPer10kVO;
import com.healthdata.entity.Per10kInstitutions;
import com.healthdata.mappers.Per10kInstitutionsMapper;
import com.healthdata.service.Per10kInstitutionsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Per10kInstitutionsServiceImpl extends ServiceImpl<Per10kInstitutionsMapper, Per10kInstitutions> implements Per10kInstitutionsService {

    @Override
    public List<InstitutionsPer10kVO> getInstitutionsPer10kByProvinceId(Integer provinceId) {
        QueryWrapper<Per10kInstitutions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", provinceId)
                .orderByAsc("year");

        List<Per10kInstitutions> entityList = baseMapper.selectList(queryWrapper);
        List<InstitutionsPer10kVO> voList = new ArrayList<>();

        for (Per10kInstitutions entity : entityList) {
            InstitutionsPer10kVO vo = new InstitutionsPer10kVO();
            if (entity.getYear() != null) {
                vo.setYear(entity.getYear());
            }
            if (entity.getPer10kInstitutions() != null) {
                vo.setTotal(entity.getPer10kInstitutions());
            }
            voList.add(vo);
        }

        return voList;
    }
}