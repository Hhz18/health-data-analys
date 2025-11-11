package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.VO.LatestMedianStatsVO;
import com.healthdata.VO.MedianIndicatorVO;
import com.healthdata.entity.LatestYearMedianStats;
import com.healthdata.mappers.LatestYearMedianStatsMapper;
import com.healthdata.service.LatestYearMedianStatsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;

@Service
public class LatestYearMedianStatsServiceImpl extends ServiceImpl<LatestYearMedianStatsMapper, LatestYearMedianStats> implements LatestYearMedianStatsService {

    @Override
    public LatestMedianStatsVO getLatestMedianStats() {
        // 查询最新年份的数据（按年份倒序取第一条）
        LambdaQueryWrapper<LatestYearMedianStats> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(LatestYearMedianStats::getYear)
                .last("LIMIT 1");

        LatestYearMedianStats latestData = baseMapper.selectOne(queryWrapper);

        if (latestData == null) {
            throw new RuntimeException("未找到最新年份的中位数数据");
        }

        // 直接在Service中转换数据结构
        List<MedianIndicatorVO> indicators = Arrays.asList(
                createIndicatorVO("totalBeds", "床位总数",
                        latestData.getMedianTotalBeds(),
                        latestData.getProvinceIdTotalBedsMedian()),

                createIndicatorVO("totalExpenditure", "医疗总费用",
                        latestData.getMedianTotalExpenditure(),
                        latestData.getProvinceIdTotalExpenditureMedian()),

                createIndicatorVO("totalInstitutions", "医疗机构总数",
                        latestData.getMedianTotalInstitutions(),
                        latestData.getProvinceIdTotalInstitutionsMedian()),

                createIndicatorVO("totalPersonnel", "医疗人员总数",
                        latestData.getMedianTotalPersonnel(),
                        latestData.getProvinceIdTotalPersonnelMedian()),

                createIndicatorVO("outpatientVisits", "门诊诊疗人次",
                        latestData.getMedianOutpatientVisits(),
                        latestData.getProvinceIdOutpatientVisitsMedian()),

                createIndicatorVO("inpatientAdmissions", "入院人数",
                        latestData.getMedianInpatientAdmissions(),
                        latestData.getProvinceIdInpatientAdmissionsMedian())
        );

        // 封装结果
        LatestMedianStatsVO result = new LatestMedianStatsVO();
        result.setYear(latestData.getYear());
        result.setIndicators(indicators);

        return result;
    }

    // 辅助方法：创建单个指标VO
    private MedianIndicatorVO createIndicatorVO(String indicator, String indicatorName,
                                                BigDecimal medianValue, Integer provinceId) {
        MedianIndicatorVO vo = new MedianIndicatorVO();
        vo.setIndicator(indicator);
        vo.setIndicatorName(indicatorName);
        vo.setMedianValue(medianValue);
        vo.setMedianProvinceId(provinceId);
        return vo;
    }
}