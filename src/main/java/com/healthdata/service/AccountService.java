package com.healthdata.service;

import com.healthdata.VO.LoginResultVO;
import com.healthdata.VO.LoginVO;
import com.healthdata.VO.RegisterVO;
import com.healthdata.VO.UserProfileVO;
import com.healthdata.entity.Account;

public interface AccountService {
    // 用户注册
    UserProfileVO register(RegisterVO registerVO);

    // 用户登录
    LoginResultVO login(LoginVO loginVO);

    // 根据用户ID查询用户
    Account getById(Integer userId);

    // 根据用户名查询用户
    Account getByUsername(String username);

    // 更新用户信息
    UserProfileVO updateProfile(Integer userId, UserProfileVO profileVO);

    // 修改密码
    void updatePassword(Integer userId, String oldPassword, String newPassword);
}