package com.healthdata.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedianIndicatorVO {
    private String indicator; // 指标英文标识
    private String indicatorName; // 指标中文名称
    private BigDecimal medianValue; // 中位数数值
    private Integer medianProvinceId; // 中位数对应的省份ID
}