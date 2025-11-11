package com.healthdata.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthdata.VO.BedsPer10kVO;
import com.healthdata.entity.Bed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BedMapper extends BaseMapper<Bed> {
}
