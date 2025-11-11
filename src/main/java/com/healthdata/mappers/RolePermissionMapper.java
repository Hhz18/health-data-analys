package com.healthdata.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthdata.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    // 批量插入角色权限关联
    int batchInsert(@Param("list") List<RolePermission> rolePermissions);
}