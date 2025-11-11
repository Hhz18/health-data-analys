package com.healthdata.VO;

import com.healthdata.enums.RoleType;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class RegisterVO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须为2-20字符")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,20}$",
            message = "密码需包含大小写字母和数字，长度6-20")
    private String password;

    @Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "邮箱格式不正确")
    private String email; // 可为空

    @Pattern(regexp = "^$|^1[3-9]\\d{9}$",
            message = "手机号格式不正确")
    private String phone; // 可为空

    private String address; // 可为空

    private RoleType role;

}