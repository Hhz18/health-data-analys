package com.healthdata.aspect;

import com.healthdata.annotation.RequirePermission;
import com.healthdata.entity.Account;
import com.healthdata.service.AccountService;
import com.healthdata.service.PermissionService;
import com.healthdata.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// 权限校验切面（AOP拦截器）

@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionService permissionService;

    // 拦截所有带@RequirePermission注解的方法
    @Pointcut("@annotation(requirePermission)")
    public void permissionPointcut(RequirePermission requirePermission) {}

    @Before("permissionPointcut(requirePermission)")
    public void checkPermission(RequirePermission requirePermission) {
        // 1. 获取当前用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").substring(7);
        Integer userId = jwtUtils.getUserIdFromToken(token);
        Account account = accountService.getById(userId);

        // 2. 校验权限
        boolean hasPermission = permissionService.hasPermission(
                account.getRole(),
                requirePermission.resource(),
                requirePermission.action()
        );
        if (!hasPermission) {
            throw new RuntimeException("权限不足：无" + requirePermission.resource() + ":" + requirePermission.action() + "权限");
        }
    }
}