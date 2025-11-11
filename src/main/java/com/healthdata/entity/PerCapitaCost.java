package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("per_capita_cost")
public class PerCapitaCost implements Serializable {
    @TableId(value = "province_id")
    private Integer provinceId;

    private Integer year;

    @TableField("per_capita_cost")
    private BigDecimal perCapitaCost;
}