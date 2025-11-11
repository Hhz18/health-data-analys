package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("province")
public class Province {
    @TableId("province_id")
    private Integer provinceId;

    @TableField("province_name")
    private String provinceName;
}