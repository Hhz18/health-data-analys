package com.healthdata.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AgeDistributionVO {
    private String component; // 年龄段（如"0-14"）
    private BigDecimal share;    // 人口数量（万人）
}