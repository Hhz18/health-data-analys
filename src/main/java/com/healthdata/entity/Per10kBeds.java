// com.healthdata.entity.Per10kBeds
package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("per_10k_beds")
public class Per10kBeds implements Serializable {
    @TableId(value = "province_id") // 主键ID
    private Integer provinceId; // 省份ID

    private Integer year;          // 统计年份

    @TableField("per_10k_beds")
    private BigDecimal per10kBeds; // 每万人床位数
}