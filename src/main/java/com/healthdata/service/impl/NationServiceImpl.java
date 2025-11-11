package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthdata.VO.BaseStatVO;
import com.healthdata.VO.NationLatestVO;
import com.healthdata.VO.ServiceStatVO;
import com.healthdata.VO.ServiceYearDataVO;
import com.healthdata.VO.YearDataVO;
import com.healthdata.entity.Nation;
import com.healthdata.mappers.NationMapper;
import com.healthdata.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NationServiceImpl implements NationService {

    @Autowired
    private NationMapper nationMapper;

    @Override
    public NationLatestVO getLatest() {
        // 查询最新年份
        Integer latestYear = nationMapper.selectList(null).stream()
                .map(Nation::getYear)
                .max(Integer::compare)
                .orElse(null);

        if (latestYear == null) return null;

        // 查询该年份数据
        Nation nation = nationMapper.selectById(latestYear);
        if (nation == null) return null;

        // 封装VO
        NationLatestVO vo = new NationLatestVO();
        vo.setYear(latestYear);

        // 人口
        BaseStatVO population = new BaseStatVO();
        population.setTotal(nation.getTotalPopulation());
        population.setLabel("人口总数（万人）");
        vo.setPopulation(population);

        // 医疗机构
        BaseStatVO institution = new BaseStatVO();
        institution.setTotal(nation.getTotalInstitutions());
        institution.setLabel("医疗机构总数");
        vo.setInstitution(institution);

        // 医疗人员
        BaseStatVO personnel = new BaseStatVO();
        personnel.setTotal(nation.getTotalPersonnel());
        personnel.setLabel("医疗人员总数");
        vo.setPersonnel(personnel);

        // 床位
        BaseStatVO bed = new BaseStatVO();
        bed.setTotal(nation.getTotalBeds());
        bed.setLabel("床位总数");
        vo.setBed(bed);

        // 医疗服务
        ServiceStatVO service = new ServiceStatVO();
        service.setOutpatientVisits(nation.getOutpatientVisits());
        service.setInpatientAdmissions(nation.getInpatientAdmissions());
        service.setLabel("医疗服务");
        vo.setService(service);

        // 医疗费用
        BaseStatVO cost = new BaseStatVO();
        cost.setTotal(nation.getTotalExpenditure());
        cost.setLabel("医疗总费用（亿元）");
        vo.setCost(cost);

        return vo;
    }

    @Override
    public List<YearDataVO> getPopulationYearsData() {
        return nationMapper.selectList(null).stream()
                .map(nation -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(nation.getYear());
                    vo.setTotal(nation.getTotalPopulation());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<YearDataVO> getInstitutionYearsData() {
        return nationMapper.selectList(null).stream()
                .map(nation -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(nation.getYear());
                    vo.setTotal(nation.getTotalInstitutions());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<YearDataVO> getPersonnelYearsData() {
        return nationMapper.selectList(null).stream()
                .map(nation -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(nation.getYear());
                    vo.setTotal(nation.getTotalPersonnel());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<YearDataVO> getBedYearsData() {
        return nationMapper.selectList(null).stream()
                .map(nation -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(nation.getYear());
                    vo.setTotal(nation.getTotalBeds());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceYearDataVO> getServiceYearsData() {
        return nationMapper.selectList(null).stream()
                .map(nation -> {
                    ServiceYearDataVO vo = new ServiceYearDataVO();
                    vo.setYear(nation.getYear());
                    vo.setOutpatientVisits(nation.getOutpatientVisits());
                    vo.setInpatientAdmissions(nation.getInpatientAdmissions());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<YearDataVO> getCostYearsData() {
        return nationMapper.selectList(null).stream()
                .map(nation -> {
                    YearDataVO vo = new YearDataVO();
                    vo.setYear(nation.getYear());
                    vo.setTotal(nation.getTotalExpenditure());
                    return vo;
                })
                .sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                .collect(Collectors.toList());
    }
}