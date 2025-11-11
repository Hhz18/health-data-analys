package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("per_10k_personnel")
public class Per10kPersonnel {
    @TableId(value = "province_id") // 主键ID
    private Integer provinceId; // 省份ID

    private Integer year;    // 统计年份

    @TableField("per_10k_personnel")
    private BigDecimal per10kPersonnel; // 每万人医疗人员数
}