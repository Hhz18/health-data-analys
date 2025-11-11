package com.healthdata.controller;

import com.healthdata.VO.CommonResponse;
import com.healthdata.VO.RolePermissionVO;
import com.healthdata.VO.UserProfileVO;
import com.healthdata.entity.Account;
import com.healthdata.entity.RolePermission;
import com.healthdata.enums.RoleType;
import com.healthdata.mappers.AccountMapper;
import com.healthdata.mappers.RolePermissionMapper;
import com.healthdata.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionService permissionService;

    // 为角色分配权限（覆盖原有权限）
    @PutMapping("/role/permissions")
    @Transactional
    public CommonResponse<Void> assignRolePermissions(@Validated @RequestBody RolePermissionVO vo) {
        RoleType role = vo.getRole();
        List<Integer> permissionIds = vo.getPermissionIds();

        // 1. 删除角色原有权限
        rolePermissionMapper.delete(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<RolePermission>()
                        .eq("role", role)
        );

        // 2. 添加新权限
        List<RolePermission> rolePermissions = permissionIds.stream()
                .map(permId -> {
                    RolePermission rp = new RolePermission();
                    rp.setRole(role);
                    rp.setPermissionId(permId);
                    return rp;
                })
                .collect(Collectors.toList());
        rolePermissionMapper.batchInsert(rolePermissions); // 需在Mapper中实现批量插入

        return CommonResponse.success(null, "权限分配成功");
    }

    // 查询角色当前权限
    @GetMapping("/role/{role}/permissions")
    public CommonResponse<List<String>> getRolePermissions(@PathVariable RoleType role) {
        return CommonResponse.success(
                permissionService.getRolePermissions(role).stream().collect(Collectors.toList()),
                "查询成功"
        );
    }


    /**
     * 获取所有用户列表
     * @return 所有用户的基本信息列表
     */
    @GetMapping("/users")
    public CommonResponse<List<UserProfileVO>> getAllUsers() {
        List<Account> accounts = accountMapper.selectList(null);
        List<UserProfileVO> userProfileVOs = accounts.stream()
                .map(account -> {
                    UserProfileVO vo = new UserProfileVO();
                    BeanUtils.copyProperties(account, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        return CommonResponse.success(userProfileVOs, "获取用户列表成功");
    }


    /**
     * 修改用户信息
     * @param userId 用户ID
     * @param updatedUser 更新的用户信息
     * @return 操作结果
     */
    @PutMapping("/users/{userId}")
    public CommonResponse<Void> updateUser(
            @PathVariable Integer userId,
            @RequestBody UserProfileVO updatedUser) {

        // 检查用户是否存在
        Account account = accountMapper.selectById(userId);

        // 复制前端传入的可修改字段
        BeanUtils.copyProperties(updatedUser, account, "userId", "password");

        // 更新数据库
        int rows = accountMapper.updateById(account);

        if (rows > 0) {
            return CommonResponse.success(null, "用户信息更新成功");
        }
        return CommonResponse.fail(404, "用户不存在或更新失败");
    }


    /**
     * 删除指定用户
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/users/{userId}")
    public CommonResponse<Void> deleteUser(@PathVariable Integer userId) {
        // 1. 校验用户是否存在
        Account account = accountMapper.selectById(userId);
        if (account == null) {
            return CommonResponse.fail(404, "用户不存在");
        }

        // 2. 安全校验：禁止删除管理员账户（可选，根据业务需求调整）
        if (RoleType.ADMIN.equals(account.getRole())) {
            return CommonResponse.fail(403, "禁止删除管理员账户");
        }

        // 3. 执行删除操作（MyBatis-Plus的deleteById方法）
        int rows = accountMapper.deleteById(userId);

        // 4. 根据删除结果返回响应
        if (rows > 0) {
            return CommonResponse.success(null, "用户删除成功");
        } else {
            return CommonResponse.fail(500, "删除失败，请重试");
        }
    }


    /**
     * 批量删除用户（可选接口，根据业务需求决定是否保留）
     * @param userIds 用户ID列表
     * @return 操作结果
     */
    @DeleteMapping("/users/batch")
    public CommonResponse<Void> batchDeleteUsers(@RequestBody List<Integer> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return CommonResponse.fail(400, "请选择需要删除的用户");
        }

        // 校验是否包含管理员账户（可选）
        List<Account> accounts = accountMapper.selectBatchIds(userIds);
        boolean containsAdmin = accounts.stream()
                .anyMatch(account -> RoleType.ADMIN.equals(account.getRole()));
        if (containsAdmin) {
            return CommonResponse.fail(403, "包含管理员账户，禁止批量删除");
        }

        // 执行批量删除（MyBatis-Plus的deleteBatchIds方法）
        int rows = accountMapper.deleteBatchIds(userIds);

        if (rows > 0) {
            return CommonResponse.success(null, "成功删除 " + rows + " 个用户");
        } else {
            return CommonResponse.fail(500, "批量删除失败");
        }
    }
}