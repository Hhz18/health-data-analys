package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.healthdata.VO.DataItemVO;
import com.healthdata.VO.ProvinceDataVO;
import com.healthdata.entity.ProvinceHealthStatistics;
import com.healthdata.mappers.ProvinceHealthStatisticsMapper;
import com.healthdata.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ProvinceHealthStatisticsMapper statisticsMapper;

    // 支持的数据类型（与实体类getter方法对应）
    private static final Set<String> SUPPORTED_DATA_TYPES = new HashSet<>(Arrays.asList(
            "population", "institution", "personnel", "bedCount",
            "cost", "outpatient", "inpatient"
    ));

    @Override
    public List<ProvinceDataVO> getLatestProvinceData(List<Integer> provinceIds, List<String> dataTypes) {
        // 1. 获取最新年份
        Integer latestYear = getLatestYear();
        if (latestYear == null) {
            return Collections.emptyList();
        }

        // 2. 构建查询条件（最新年份 + 可选省份过滤）
        LambdaQueryWrapper<ProvinceHealthStatistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProvinceHealthStatistics::getYear, latestYear);
        if (provinceIds != null && !provinceIds.isEmpty()) {
            queryWrapper.in(ProvinceHealthStatistics::getProvinceId, provinceIds);
        }

        // 3. 查询汇总表数据
        List<ProvinceHealthStatistics> statsList = statisticsMapper.selectList(queryWrapper);
        if (statsList.isEmpty()) {
            return Collections.emptyList();
        }

        // 4. 确定需要返回的数据类型（过滤不支持的类型）
        List<String> targetDataTypes = dataTypes == null || dataTypes.isEmpty()
                ? new ArrayList<>(SUPPORTED_DATA_TYPES)
                : dataTypes.stream()
                .filter(SUPPORTED_DATA_TYPES::contains)
                .collect(Collectors.toList());

        // 5. 转换为前端格式
        return statsList.stream().map(stat -> {
            ProvinceDataVO vo = new ProvinceDataVO();
            vo.setId(stat.getProvinceId());

            List<DataItemVO> dataItems = targetDataTypes.stream().map(dataType -> {
                Object value = getFieldValue(stat, dataType);
                return new DataItemVO(dataType, value);
            }).collect(Collectors.toList());

            vo.setData(dataItems);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取最新统计年份
     */
    private Integer getLatestYear() {
        LambdaQueryWrapper<ProvinceHealthStatistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProvinceHealthStatistics::getYear);
        List<ProvinceHealthStatistics> stats = statisticsMapper.selectList(queryWrapper);

        return stats.stream()
                .map(ProvinceHealthStatistics::getYear)
                .max(Integer::compare)
                .orElse(null);
    }

    /**
     * 直接通过实体类getter方法获取字段值（避免反射错误）
     */
    private Object getFieldValue(ProvinceHealthStatistics stat, String dataType) {
        switch (dataType) {
            case "population":
                return stat.getTotalPopulation() != null ? stat.getTotalPopulation() : 0;
            case "institution":
                return stat.getTotalInstitutions() != null ? stat.getTotalInstitutions() : 0;
            case "personnel":
                return stat.getTotalPersonnel() != null ? stat.getTotalPersonnel() : 0;
            case "bedCount":
                return stat.getTotalBeds() != null ? stat.getTotalBeds() : 0;
            case "cost":
                return stat.getTotalExpenditure() != null ? stat.getTotalExpenditure() : BigDecimal.ZERO;
            case "outpatient":
                return stat.getOutpatientVisits() != null ? stat.getOutpatientVisits() : 0L;
            case "inpatient":
                return stat.getInpatientAdmissions() != null ? stat.getInpatientAdmissions() : 0L;
            default:
                return 0;
        }
    }
}