package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.VO.BedsPer10kVO;
import com.healthdata.entity.Per10kBeds;
import com.healthdata.mappers.Per10kBedsMapper;
import com.healthdata.service.Per10kBedsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Per10kBedsServiceImpl extends ServiceImpl<Per10kBedsMapper, Per10kBeds> implements Per10kBedsService {

    @Override
    public List<BedsPer10kVO> getBedsPer10kByProvinceId(Integer provinceId) {
        // 1. 创建查询条件，明确指定实体类 Per10kBeds
        QueryWrapper<Per10kBeds> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", provinceId)  // 条件：匹配省份ID
                .orderByAsc("year");             // 排序：按年份升序

        // 2. 通过当前 Service 关联的 Mapper 查询数据（确保实体类与表匹配）
        List<Per10kBeds> entityList = baseMapper.selectList(queryWrapper);

        // 3. 转换为 VO 并返回
        return entityList.stream().map(entity -> {
            BedsPer10kVO vo = new BedsPer10kVO();
            // 注意：年份字段 entity.getYear() 是 Date 类型，需要转换为 Integer
            vo.setYear(entity.getYear());
            vo.setTotal(entity.getPer10kBeds());
            return vo;
        }).collect(Collectors.toList());
    }
}