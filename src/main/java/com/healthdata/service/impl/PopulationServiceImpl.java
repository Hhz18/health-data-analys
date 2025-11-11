package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.VO.AgeDistributionVO;
import com.healthdata.VO.MarketShareVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Population;
import com.healthdata.mappers.PopulationMapper;
import com.healthdata.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PopulationServiceImpl extends ServiceImpl<PopulationMapper, Population> implements PopulationService {

    @Autowired
    private PopulationMapper populationMapper;

    @Override
    public Population getLatestByProvinceId(Integer provinceId) {
        List<Population> populations = populationMapper.selectList(
                new QueryWrapper<Population>().eq("province_id", provinceId)
        );
        if (populations.isEmpty()) return null;

        Integer latestYear = populations.stream()
                .map(Population::getYear)
                .max(Integer::compare)
                .orElse(null);
        if (latestYear == null) return null;

        return populationMapper.selectOne(
                new QueryWrapper<Population>()
                        .eq("province_id", provinceId)
                        .eq("year", latestYear)
        );
    }

    @Override
    public List<YearDataVO> getYearsDataByProvinceId(Integer provinceId) {
        return populationMapper.selectList(
                        new QueryWrapper<Population>().eq("province_id", provinceId)
                ).stream()
                .map(population -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(population.getYear());
                    vo.setTotal(population.getTotalPopulation());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }

    // 修正后的 createVO 方法及日志打印
    private AgeDistributionVO createVO(String component, BigDecimal value) {
        AgeDistributionVO vo = new AgeDistributionVO();
        vo.setComponent(component);
        vo.setShare(value != null ? value : BigDecimal.ZERO);
        return vo;
    }

    @Override
    public List<AgeDistributionVO> getLatestAgeDistribution(Integer provinceId) {
        LambdaQueryWrapper<Population> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Population::getProvinceId, provinceId)
                .orderByDesc(Population::getYear)
                .last("LIMIT 1");

        Population latestData = baseMapper.selectOne(queryWrapper);
        if (latestData == null) {
            throw new IllegalArgumentException("未找到省份ID为" + provinceId + "的最新人口数据");
        }

        // 修正日志打印：使用字符串拼接或 String.format
        log.debug("查询到的年龄数据：");
        log.debug("0-14岁: " + latestData.getAge014()); // 字符串拼接（推荐）
        log.debug(String.format("15-20岁: %s", latestData.getAge1520())); // 格式化工具类

        return Arrays.asList(
                createVO("0-14", latestData.getAge014()),
                createVO("15-20", latestData.getAge1520()),
                createVO("20-60", latestData.getAge2060()),
                createVO("60以上", latestData.getAge60Plus())
        );
    }



    @Override
    public List<MarketShareVO> getLatestGenderData(Integer provinceId) {
        // 1. 构造查询条件：按省份 + 年份倒序取最新
        LambdaQueryWrapper<Population> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Population::getProvinceId, provinceId)
                .orderByDesc(Population::getYear)
                .last("LIMIT 1"); // 只取最新一条

        // 2. 执行查询
        Population data = baseMapper.selectOne(wrapper);

        // 3. 映射为前端 VO
        return Arrays.asList(
                new MarketShareVO("男", data.getMalePopulation()),
                new MarketShareVO("女", data.getFemalePopulation())
        );
    }

}