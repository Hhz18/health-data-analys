package com.healthdata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthdata.entity.Permission;
import com.healthdata.entity.RolePermission;
import com.healthdata.enums.RoleType;
import com.healthdata.mappers.PermissionMapper;
import com.healthdata.mappers.RolePermissionMapper;
import com.healthdata.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Set<String> getRolePermissions(RoleType role) {
        // 1. 查询角色关联的权限ID
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(
                new QueryWrapper<RolePermission>().eq("role", role)
        );
        List<Integer> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 2. 查询权限详情并转换为"resource:action"格式
        List<Permission> permissions = baseMapper.selectBatchIds(permissionIds);
        return permissions.stream()
                .map(p -> p.getResource() + ":" + p.getAction())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasPermission(RoleType role, String resource, String action) {
        // 检查角色是否拥有"resource:action"权限
        Set<String> permissions = getRolePermissions(role);
        return permissions.contains(resource + ":" + action);
    }
}