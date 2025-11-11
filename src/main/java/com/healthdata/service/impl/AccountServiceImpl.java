package com.healthdata.service.impl;

import com.healthdata.VO.LoginResultVO;
import com.healthdata.VO.LoginVO;
import com.healthdata.VO.RegisterVO;
import com.healthdata.VO.UserProfileVO;
import com.healthdata.entity.Account;
import com.healthdata.mappers.AccountMapper;
import com.healthdata.service.AccountService;
import com.healthdata.service.CaptchaService;
import com.healthdata.utils.JwtUtils;
import com.healthdata.enums.RoleType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private JwtUtils jwtUtils; // 注入JWT工具类

    @Autowired
    private CaptchaService captchaService; // 注入验证码服务

    // 密码加密器
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public UserProfileVO register(RegisterVO registerVO) {
        // 检查用户名是否已存在
        Account existingUser = getByUsername(registerVO.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已被占用");
        }

        // 转换VO为实体
        Account account = new Account();
        BeanUtils.copyProperties(registerVO, account);

        // 设置默认角色（如果前端未传递角色）
        if (account.getRole() == null) {
            account.setRole(RoleType.USER); // 默认普通用户
        }

        // 密码加密存储
        account.setPassword(passwordEncoder.encode(registerVO.getPassword()));

        // 插入数据库
        accountMapper.insert(account);

        // 返回用户信息（不含密码）
        UserProfileVO result = new UserProfileVO();
        BeanUtils.copyProperties(account, result);
        return result;
    }

    // 登录方法修正（使用JWT生成令牌）
    @Override
    public LoginResultVO login(LoginVO loginVO) {
        // 查询用户
        Account account = getByUsername(loginVO.getUsername());
        if (account == null) {
            throw new RuntimeException("用户名或密码错误");
        }

//        // 高危角色（如管理员）强制验证验证码
//        if (RoleType.ADMIN.equals(account.getRole())) {
//            if (!captchaService.validateCaptcha(loginVO.getUsername(), loginVO.getCaptcha())) {
//                throw new RuntimeException("验证码错误或已过期");
//            }
//        }

        // 验证密码
        if (!passwordEncoder.matches(loginVO.getPassword(), account.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 生成JWT令牌（包含角色信息）
        String token = jwtUtils.generateToken(account.getUserId(), account.getUsername(), account.getRole());
        String expireTime = LocalDateTime.now().plusHours(2)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 封装返回结果
        LoginResultVO result = new LoginResultVO();
        result.setToken(token);
        result.setExpireTime(expireTime);

        UserProfileVO userVO = new UserProfileVO();
        BeanUtils.copyProperties(account, userVO);
        result.setUser(userVO);

        return result;
    }

    @Override
    public Account getById(Integer userId) {
        return accountMapper.selectById(userId);
    }

    @Override
    public Account getByUsername(String username) {
        return accountMapper.selectByUsername(username);
    }

    @Override
    @Transactional
    public UserProfileVO updateProfile(Integer userId, UserProfileVO profileVO) {
        Account account = getById(userId);
        if (account == null) {
            throw new RuntimeException("用户不存在");
        }

        // 更新可修改字段
        if (profileVO.getUsername() != null) {
            account.setUsername(profileVO.getUsername());
        }
        if (profileVO.getEmail() != null) {
            account.setEmail(profileVO.getEmail());
        }
        if (profileVO.getPhone() != null) {
            account.setPhone(profileVO.getPhone());
        }
        if (profileVO.getAddress() != null) {
            account.setAddress(profileVO.getAddress());
        }

        accountMapper.updateById(account);

        // 返回更新后的信息
        UserProfileVO result = new UserProfileVO();
        BeanUtils.copyProperties(account, result);
        return result;
    }

    @Override
    @Transactional
    public void updatePassword(Integer userId, String oldPassword, String newPassword) {
        Account account = getById(userId);
        if (account == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证原密码
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            throw new RuntimeException("原密码错误");
        }

        // 更新新密码（加密存储）
        account.setPassword(passwordEncoder.encode(newPassword));
        accountMapper.updateById(account);
    }

/*    // 生成简单令牌（实际项目中替换为JWT生成逻辑）
    private String generateToken(Integer userId) {
        return "TOKEN_" + userId + "_" + System.currentTimeMillis();
    }*/
}