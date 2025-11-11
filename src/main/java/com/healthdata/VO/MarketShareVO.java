package com.healthdata.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MarketShareVO {
    private String component; // 男/女
    private BigDecimal share; // 人口数（直接用 BigDecimal 保持精度）

    public MarketShareVO(String component, BigDecimal share) {
        this.component = component;
        this.share = share;
    }
}