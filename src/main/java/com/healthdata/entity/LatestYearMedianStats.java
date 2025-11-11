package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("latest_year_median_stats")
public class LatestYearMedianStats {
    @TableId(type = IdType.AUTO)
    private Integer id; // 记录ID
    private Integer year; // 统计年份
    @TableField("median_total_beds")
    private BigDecimal medianTotalBeds; // 床位总数中位数
    @TableField("median_total_expenditure")
    private BigDecimal medianTotalExpenditure; // 医疗总费用中位数(亿元)
    @TableField("median_total_institutions")
    private BigDecimal medianTotalInstitutions; // 医疗机构总数中位数
    @TableField("median_total_personnel")
    private BigDecimal medianTotalPersonnel; // 医疗人员总数中位数
    @TableField("median_outpatient_visits")
    private BigDecimal medianOutpatientVisits; // 门诊诊疗人次中位数（万人）
    @TableField("median_inpatient_admissions")
    private BigDecimal medianInpatientAdmissions; // 入院人数中位数（万人）
    @TableField("province_id_total_beds_median")
    private Integer provinceIdTotalBedsMedian; // 床位总数中位数对应的省份ID
    @TableField("province_id_total_expenditure_median")
    private Integer provinceIdTotalExpenditureMedian; // 医疗总费用中位数对应的省份ID
    @TableField("province_id_total_institutions_median")
    private Integer provinceIdTotalInstitutionsMedian; // 医疗机构总数中位数对应的省份ID
    @TableField("province_id_total_personnel_median")
    private Integer provinceIdTotalPersonnelMedian; // 医疗人员总数中位数对应的省份ID
    @TableField("province_id_outpatient_visits_median")
    private Integer provinceIdOutpatientVisitsMedian; // 门诊诊疗人次中位数对应的省份ID
    @TableField("province_id_inpatient_admissions_median")
    private Integer provinceIdInpatientAdmissionsMedian; // 入院人数中位数对应的省份ID
}