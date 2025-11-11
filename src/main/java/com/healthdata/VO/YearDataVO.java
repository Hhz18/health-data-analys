package com.healthdata.VO;

import lombok.Data;

@Data
public class YearDataVO {
    private Integer year;
    private Number total; // 单一指标的年份数据
}