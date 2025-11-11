package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("province_health_statistics")
public class ProvinceHealthStatistics {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("province_id")
    private Integer provinceId;

    @TableField("province_name")
    private String provinceName;

    private Integer year;

    @TableField("total_population")
    private Integer totalPopulation;

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

    @TableField("update_time")
    private Date updateTime;
}