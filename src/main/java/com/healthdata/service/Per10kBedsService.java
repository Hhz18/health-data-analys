package com.healthdata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthdata.VO.BedsPer10kVO;
import com.healthdata.entity.Per10kBeds;

import java.util.List;

public interface Per10kBedsService extends IService<Per10kBeds> {
    // 新增查询方法：根据省份ID获取历年每万人床位数
    List<BedsPer10kVO> getBedsPer10kByProvinceId(Integer provinceId);
}