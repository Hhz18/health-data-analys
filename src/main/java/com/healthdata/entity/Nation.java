package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("national_stat")
public class Nation {
    @TableId("year")
    private Integer year;

    @TableField("total_population")
    private BigDecimal totalPopulation;

    @TableField("total_beds")
    private Integer totalBeds;

    @TableField("total_expenditure")
    private BigDecimal totalExpenditure;

    @TableField("total_institutions")
    private Integer totalInstitutions;

    @TableField("total_personnel")
    private Integer totalPersonnel;

    @TableField("outpatient_visits")
    private Long outpatientVisits;

    @TableField("inpatient_admissions")
    private Long inpatientAdmissions;
}