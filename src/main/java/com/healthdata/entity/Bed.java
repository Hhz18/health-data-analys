package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bed_stat")
public class Bed {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("province_id")
    private Integer provinceId;

    private Integer year;

    @TableField("total_beds")
    private Integer totalBeds;
}