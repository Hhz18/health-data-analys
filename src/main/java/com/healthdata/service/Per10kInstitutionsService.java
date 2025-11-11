package com.healthdata.service;

import com.healthdata.VO.InstitutionsPer10kVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthdata.entity.Per10kInstitutions;

import java.util.List;

public interface Per10kInstitutionsService extends IService<Per10kInstitutions> {
    List<InstitutionsPer10kVO> getInstitutionsPer10kByProvinceId(Integer provinceId);
}