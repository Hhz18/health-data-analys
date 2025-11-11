package com.healthdata.VO;

import com.healthdata.enums.RoleType;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class LoginVO {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private RoleType role;

    private String captcha; // 验证码（可选，高危操作强制要求）
}