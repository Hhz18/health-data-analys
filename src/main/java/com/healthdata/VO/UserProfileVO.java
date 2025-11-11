package com.healthdata.VO;

import com.healthdata.enums.RoleType;
import lombok.Data;

@Data
public class UserProfileVO {
    private Integer userId;
    private String username;
    private String email;
    private String phone;
    private String address;
    private RoleType role;
}