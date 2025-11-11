package com.healthdata.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BedsPer10kVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer year;

    /**
     * 每万人床位数
     */
    private BigDecimal total;
}