package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthdata.VO.BedsPer10kVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Bed;
import com.healthdata.entity.Per10kBeds;
import com.healthdata.mappers.BedMapper;
import com.healthdata.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.extension.toolkit.Db.list;

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private BedMapper bedMapper;

    @Override
    public Bed getLatestByProvinceId(Integer provinceId) {
        // 获取最新年份
        List<Bed> beds = bedMapper.selectList(
                new QueryWrapper<Bed>().eq("province_id", provinceId)
        );
        if (beds.isEmpty()) return null;

        Integer latestYear = beds.stream()
                .map(Bed::getYear)
                .max(Integer::compare)
                .orElse(null);
        if (latestYear == null) return null;

        // 查询该年份数据
        return bedMapper.selectOne(
                new QueryWrapper<Bed>()
                        .eq("province_id", provinceId)
                        .eq("year", latestYear)
        );
    }

    @Override
    public List<YearDataVO> getYearsDataByProvinceId(Integer provinceId) {
        return bedMapper.selectList(
                        new QueryWrapper<Bed>().eq("province_id", provinceId)
                ).stream()
                .map(bed -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(bed.getYear());
                    vo.setTotal(bed.getTotalBeds());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Bed> getBedsByProvinceAndYear(Integer provinceId, Integer year) {
        return bedMapper.selectList(
                new QueryWrapper<Bed>()
                        .eq("province_id", provinceId)
                        .eq("year", year)
        );
    }
}