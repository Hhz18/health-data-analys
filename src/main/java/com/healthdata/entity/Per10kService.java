package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("per_10k_service")
public class Per10kService {
    @TableId(value = "province_id") // 主键ID
    private Integer provinceId; // 省份ID
    private Integer year;       // 统计年份（使用Integer类型）

    @TableField("per_10k_outpatient_visits")
    private BigDecimal outpatientVisitsPer10k; // 每万人门诊诊疗人次

    @TableField("per_10k_inpatient_admissions")
    private BigDecimal inpatientAdmissionsPer10k; // 每万人住院人数
}