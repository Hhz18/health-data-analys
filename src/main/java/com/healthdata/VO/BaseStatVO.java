package com.healthdata.VO;

import lombok.Data;

@Data
public class BaseStatVO {
    private Number total; // 支持数字类型（int/long/bigdecimal）
    private String label;
}