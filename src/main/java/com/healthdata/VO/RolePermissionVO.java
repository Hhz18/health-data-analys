package com.healthdata.VO;

import com.healthdata.enums.RoleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.List;

@Data
public class RolePermissionVO {
    @NotNull(message = "角色不能为空")
    private RoleType role; // 角色
    @NotEmpty(message = "权限ID列表不能为空")
    private List<Integer> permissionIds; // 权限ID列表
}