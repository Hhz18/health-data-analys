package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("cost_stat")
public class Cost {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("province_id")
    private Integer provinceId;

    private Integer year;

    @TableField("total_expenditure")
    private BigDecimal totalExpenditure;
}