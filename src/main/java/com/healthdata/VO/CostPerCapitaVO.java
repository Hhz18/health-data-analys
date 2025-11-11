package com.healthdata.VO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CostPerCapitaVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 人均费用(元)
     */
    private BigDecimal total;
}