package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("service_stat")
public class Serving {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("province_id")
    private Integer provinceId;

    private Integer year;

    @TableField("outpatient_visits")
    private Long outpatientVisits;

    @TableField("inpatient_admissions")
    private Long inpatientAdmissions;
}