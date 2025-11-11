package com.healthdata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthdata.entity.Permission;
import com.healthdata.enums.RoleType;

import java.util.List;
import java.util.Set;

public interface PermissionService extends IService<Permission> {
    // 获取角色的所有权限（resource:action格式）
    Set<String> getRolePermissions(RoleType role);

    // 检查角色是否有指定权限（resource + action）
    boolean hasPermission(RoleType role, String resource, String action);
}