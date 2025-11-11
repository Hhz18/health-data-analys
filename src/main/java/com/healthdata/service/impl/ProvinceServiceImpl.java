package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthdata.VO.BaseStatVO;
import com.healthdata.VO.ProvinceLatestVO;
import com.healthdata.VO.ServiceStatVO;
import com.healthdata.entity.*;
import com.healthdata.mappers.*;
import com.healthdata.service.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private static final Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private PopulationMapper populationMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private PersonnelMapper personnelMapper;
    @Autowired
    private BedMapper bedMapper;
    @Autowired
    private ServingMapper servingMapper;
    @Autowired
    private CostMapper costMapper;

    @Override
    public List<Province> getProvinces() {
        return provinceMapper.selectList(null);
    }

    @Override
    public ProvinceLatestVO getLatestByProvinceId(Integer provinceId) {
        if (provinceId == 35) {
            // 获取全国最新年份
            List<Population> populations = populationMapper.selectList(null);
            if (populations.isEmpty()) return null;

            Integer latestYear = populations.stream()
                    .map(Population::getYear)
                    .max(Integer::compare)
                    .orElse(null);
            if (latestYear == null) return null;

            // 只查最新年份的全国数据
            List<Population> yearPopulations = populationMapper.selectList(
                    new QueryWrapper<Population>().eq("year", latestYear)
            );

            ProvinceLatestVO vo = new ProvinceLatestVO();
            vo.setProvinceId(0);
            vo.setProvinceName("全国");
            vo.setYear(latestYear);

            Integer totalPopulation = yearPopulations.stream()
                    .mapToInt(Population::getTotalPopulation)
                    .sum();
            BaseStatVO populationVO = new BaseStatVO();
            populationVO.setTotal(totalPopulation);
            populationVO.setLabel("人口总数（万人）");
            vo.setPopulation(populationVO);

            // 医疗机构
            List<Institution> institutions = institutionMapper.selectList(
                    new QueryWrapper<Institution>().eq("year", latestYear)
            );
            Integer totalInstitutions = institutions.stream()
                    .mapToInt(Institution::getTotalInstitutions)
                    .sum();
            BaseStatVO institutionVO = new BaseStatVO();
            institutionVO.setTotal(totalInstitutions);
            institutionVO.setLabel("医疗机构总数");
            vo.setInstitution(institutionVO);

            // 医疗人员
            List<Personnel> personnels = personnelMapper.selectList(
                    new QueryWrapper<Personnel>().eq("year", latestYear)
            );
            Integer totalPersonnel = personnels.stream()
                    .mapToInt(Personnel::getTotalPersonnel)
                    .sum();
            BaseStatVO personnelVO = new BaseStatVO();
            personnelVO.setTotal(totalPersonnel);
            personnelVO.setLabel("医疗人员总数");
            vo.setPersonnel(personnelVO);

            // 床位
            List<Bed> beds = bedMapper.selectList(
                    new QueryWrapper<Bed>().eq("year", latestYear)
            );
            Integer totalBeds = beds.stream()
                    .mapToInt(Bed::getTotalBeds)
                    .sum();
            BaseStatVO bedVO = new BaseStatVO();
            bedVO.setTotal(totalBeds);
            bedVO.setLabel("床位总数");
            vo.setBed(bedVO);

            // 医疗服务
            List<Serving> servings = servingMapper.selectList(
                    new QueryWrapper<Serving>().eq("year", latestYear)
            );
            long totalOutpatientVisits = servings.stream()
                    .mapToLong(Serving::getOutpatientVisits)
                    .sum();
            long totalInpatientAdmissions = servings.stream()
                    .mapToLong(Serving::getInpatientAdmissions)
                    .sum();
            ServiceStatVO serviceVO = new ServiceStatVO();
            serviceVO.setOutpatientVisits(totalOutpatientVisits);
            serviceVO.setInpatientAdmissions(totalInpatientAdmissions);
            serviceVO.setLabel("医疗服务");
            vo.setService(serviceVO);

            // 费用
            List<Cost> costs = costMapper.selectList(
                    new QueryWrapper<Cost>().eq("year", latestYear)
            );
            BigDecimal totalExpenditure = costs.stream()
                    .map(Cost::getTotalExpenditure)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BaseStatVO costVO = new BaseStatVO();
            costVO.setTotal(totalExpenditure);
            costVO.setLabel("医疗总费用（亿元）");
            vo.setCost(costVO);

            return vo;
        } else {
            // 原来的省份逻辑
            // 获取省份名称
            Province province = provinceMapper.selectById(provinceId);
            if (province == null) return null;

            // 获取最新年份（从人口表取，默认所有表年份一致）
            List<Population> populations = populationMapper.selectList(
                    new QueryWrapper<Population>().eq("province_id", provinceId)
            );
            if (populations.isEmpty()) return null;

            Integer latestYear = populations.stream()
                    .map(Population::getYear)
                    .max(Integer::compare)
                    .orElse(null);
            if (latestYear == null) return null;

            // 封装VO
            ProvinceLatestVO vo = new ProvinceLatestVO();
            vo.setProvinceId(provinceId);
            vo.setProvinceName(province.getProvinceName());
            vo.setYear(latestYear);

            // 人口数据
            Population population = populationMapper.selectOne(
                    new QueryWrapper<Population>()
                            .eq("province_id", provinceId)
                            .eq("year", latestYear)
            );
            BaseStatVO populationVO = new BaseStatVO();
            populationVO.setTotal(population.getTotalPopulation());
            populationVO.setLabel("人口总数（万人）");
            vo.setPopulation(populationVO);

            // 医疗机构数据
            Institution institution = institutionMapper.selectOne(
                    new QueryWrapper<Institution>()
                            .eq("province_id", provinceId)
                            .eq("year", latestYear)
            );
            BaseStatVO institutionVO = new BaseStatVO();
            institutionVO.setTotal(institution.getTotalInstitutions());
            institutionVO.setLabel("医疗机构总数");
            vo.setInstitution(institutionVO);

            // 医疗人员数据
            Personnel personnel = personnelMapper.selectOne(
                    new QueryWrapper<Personnel>()
                            .eq("province_id", provinceId)
                            .eq("year", latestYear)
            );
            BaseStatVO personnelVO = new BaseStatVO();
            personnelVO.setTotal(personnel.getTotalPersonnel());
            personnelVO.setLabel("医疗人员总数");
            vo.setPersonnel(personnelVO);

            // 床位数据
            Bed bed = bedMapper.selectOne(
                    new QueryWrapper<Bed>()
                            .eq("province_id", provinceId)
                            .eq("year", latestYear)
            );
            BaseStatVO bedVO = new BaseStatVO();
            bedVO.setTotal(bed.getTotalBeds());
            bedVO.setLabel("床位总数");
            vo.setBed(bedVO);

            // 服务数据
            Serving serving = servingMapper.selectOne(
                    new QueryWrapper<Serving>()
                            .eq("province_id", provinceId)
                            .eq("year", latestYear)
            );
            ServiceStatVO serviceVO = new ServiceStatVO();
            serviceVO.setOutpatientVisits(serving.getOutpatientVisits());
            serviceVO.setInpatientAdmissions(serving.getInpatientAdmissions());
            serviceVO.setLabel("医疗服务");
            vo.setService(serviceVO);

            // 费用数据
            Cost cost = costMapper.selectOne(
                    new QueryWrapper<Cost>()
                            .eq("province_id", provinceId)
                            .eq("year", latestYear)
            );
            BaseStatVO costVO = new BaseStatVO();
            costVO.setTotal(cost.getTotalExpenditure());
            costVO.setLabel("医疗总费用（亿元）");
            vo.setCost(costVO);

            return vo;
        }
    }


    @Override
    public List<ProvinceLatestVO> getAllLatest() {
        List<Province> provinces = provinceMapper.selectList(null);
        return provinces.stream()
                // 调用当前对象的非静态方法getLatestByProvinceId
                .map(province -> this.getLatestByProvinceId(province.getProvinceId()))
                // 过滤掉返回值为null的元素
                .filter(Objects::nonNull)
                // 收集为列表
                .collect(Collectors.toList());
    }
}