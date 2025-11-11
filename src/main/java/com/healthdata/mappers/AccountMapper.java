package com.healthdata.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthdata.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    // 根据用户名查询用户
    Account selectByUsername(@Param("username") String username);
}