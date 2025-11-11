package com.healthdata.controller;

import com.healthdata.VO.*;
import com.healthdata.entity.Account;
import com.healthdata.service.AccountService;
import com.healthdata.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtils jwtUtils; // 注入JWT工具类

    // 用户注册
    @PostMapping("/auth/register")
    public CommonResponse<UserProfileVO> register(@Validated @RequestBody RegisterVO registerVO) {
        try {
            UserProfileVO user = accountService.register(registerVO);
            return CommonResponse.success(user, "注册成功");
        } catch (RuntimeException e) {
            return CommonResponse.fail(400, e.getMessage());
        }
    }

    // 用户登录
    @PostMapping("/auth/login")
    public CommonResponse<LoginResultVO> login(@Validated @RequestBody LoginVO loginVO) {
        try {
            LoginResultVO result = accountService.login(loginVO);
            return CommonResponse.success(result, "登录成功");
        } catch (RuntimeException e) {
            return CommonResponse.fail(401, e.getMessage());
        }
    }

    // 获取当前用户信息（简化版：实际需解析token获取userId）
    @GetMapping("/user/profile")
    public CommonResponse<UserProfileVO> getProfile(HttpServletRequest request) {
        try {
            // 实际项目中从token解析userId，这里简化为从请求头获取
            Integer userId = getUserIdFromToken(request);
            Account account = accountService.getById(userId);

            UserProfileVO profileVO = new UserProfileVO();
            BeanUtils.copyProperties(account, profileVO);
            return CommonResponse.success(profileVO, "success");
        } catch (RuntimeException e) {
            return CommonResponse.fail(401, e.getMessage());
        }
    }

    // 修改个人信息
    @PutMapping("/user/profile")
    public CommonResponse<UserProfileVO> updateProfile(
            HttpServletRequest request,
            @RequestBody UserProfileVO profileVO) {
        try {
            Integer userId = getUserIdFromToken(request);
            UserProfileVO updated = accountService.updateProfile(userId, profileVO);
            return CommonResponse.success(updated, "信息修改成功");
        } catch (RuntimeException e) {
            return CommonResponse.fail(400, e.getMessage());
        }
    }

    // 修改密码
    @PutMapping("/user/password")
    public CommonResponse<Void> updatePassword(
            HttpServletRequest request,
            @Validated @RequestBody PasswordVO passwordVO) {
        try {
            Integer userId = getUserIdFromToken(request);
            accountService.updatePassword(userId, passwordVO.getOldPassword(), passwordVO.getNewPassword());
            return CommonResponse.success(null, "密码修改成功，请重新登录");
        } catch (RuntimeException e) {
            return CommonResponse.fail(400, e.getMessage());
        }
    }

    // 从请求头中解析用户ID（修正版）
    private Integer getUserIdFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("请先登录（令牌格式错误）");
        }

        String token = authorizationHeader.substring(7); // 去除"Bearer "前缀
        if (!jwtUtils.validateToken(token)) {
            throw new RuntimeException("令牌已过期，请重新登录");
        }

        return jwtUtils.getUserIdFromToken(token);
    }
}