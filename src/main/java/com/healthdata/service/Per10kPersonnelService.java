package com.healthdata.service;

import com.healthdata.VO.PersonnelPer10kVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthdata.entity.Per10kPersonnel;

import java.util.List;

public interface Per10kPersonnelService extends IService<Per10kPersonnel> {
    /**
     * 获取指定省份的历年每万人医疗人员数
     */
    List<PersonnelPer10kVO> getPersonnelPer10kByProvinceId(Integer provinceId);
}