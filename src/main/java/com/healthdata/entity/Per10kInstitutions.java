package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("per_10k_institutions")
public class Per10kInstitutions implements Serializable {
    @TableId(value = "province_id")
    private Integer provinceId;
    private Integer year;

    @TableField("per_10k_institutions")
    private BigDecimal per10kInstitutions;
}