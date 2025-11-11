package com.healthdata.VO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ServicePer10kVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 每万人服务量（门诊或住院）
     */
    private BigDecimal total;
}