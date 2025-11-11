package com.healthdata.VO;

import lombok.Data;
import java.util.List;

@Data
public class ProvinceDataVO {
    private Integer id; // 省份ID
    private List<DataItemVO> data; // 统计数据列表
}