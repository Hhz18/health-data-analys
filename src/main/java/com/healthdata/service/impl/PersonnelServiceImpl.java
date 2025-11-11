package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Personnel;
import com.healthdata.mappers.PersonnelMapper;
import com.healthdata.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonnelServiceImpl extends ServiceImpl<PersonnelMapper, Personnel> implements PersonnelService {

    @Autowired
    private PersonnelMapper personnelMapper;

    @Override
    public Personnel getLatestByProvinceId(Integer provinceId) {
        List<Personnel> personnels = personnelMapper.selectList(
                new QueryWrapper<Personnel>().eq("province_id", provinceId)
        );
        if (personnels.isEmpty()) return null;

        Integer latestYear = personnels.stream()
                .map(Personnel::getYear)
                .max(Integer::compare)
                .orElse(null);
        if (latestYear == null) return null;

        return personnelMapper.selectOne(
                new QueryWrapper<Personnel>()
                        .eq("province_id", provinceId)
                        .eq("year", latestYear)
        );
    }

    @Override
    public List<YearDataVO> getYearsDataByProvinceId(Integer provinceId) {
        return personnelMapper.selectList(
                        new QueryWrapper<Personnel>().eq("province_id", provinceId)
                ).stream()
                .map(personnel -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(personnel.getYear());
                    vo.setTotal(personnel.getTotalPersonnel());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getLatestPersonnelComponent(Integer provinceId) {
        // 构建查询条件：按省份ID查询，按年份倒序排序，取最新一条数据
        LambdaQueryWrapper<Personnel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Personnel::getProvinceId, provinceId)
                .orderByDesc(Personnel::getYear)
                .last("LIMIT 1");

        // 执行查询，获取最新的一条数据
        Personnel latestData = baseMapper.selectOne(queryWrapper);
        if (latestData == null) {
            throw new IllegalArgumentException("未找到省份ID为" + provinceId + "的最新医疗人员数据");
        }

        // 转换为前端需要的格式
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> doctorMap = new HashMap<>();
        doctorMap.put("component", "医生");
        doctorMap.put("share", latestData.getPhysicians());

        Map<String, Object> nurseMap = new HashMap<>();
        nurseMap.put("component", "护士");
        nurseMap.put("share", latestData.getNurses());

        resultList.add(doctorMap);
        resultList.add(nurseMap);

        return resultList;
    }
}