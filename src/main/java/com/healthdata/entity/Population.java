package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("population_stat")
public class Population {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("province_id")
    private Integer provinceId;

    private Integer year;

    @TableField("total_population")
    private Integer totalPopulation;

    @TableField("male_population")
    private BigDecimal malePopulation;

    @TableField("female_population")
    private BigDecimal femalePopulation;
    // 关键修正：确保属性名与SQL别名一致（age0_14），并显式映射数据库字段
    @TableField(value = "age_0_14")  // 数据库字段名：age_0_14
    private BigDecimal age014;      // 属性名与SQL别名一致：age0_14

    @TableField(value = "age_15_20")
    private BigDecimal age1520;     // 属性名：age15_20（与SQL别名一致）

    @TableField(value = "age_20_60")
    private BigDecimal age2060;     // 属性名：age20_60

    @TableField(value = "age_60_plus")
    private BigDecimal age60Plus;    // 属性名：age60Plus（与SQL别名一致）
}