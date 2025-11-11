package com.healthdata.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataItemVO {
    private String name; // 数据类型名称
    private Object value; // 数据值
}