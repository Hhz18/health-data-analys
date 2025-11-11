package com.healthdata.VO;

import lombok.Data;

import java.util.List;

@Data
public class LatestMedianStatsVO {
    private Integer year; // 统计年份
    private List<MedianIndicatorVO> indicators; // 指标列表
}