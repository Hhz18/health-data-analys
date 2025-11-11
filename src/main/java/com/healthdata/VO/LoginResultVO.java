package com.healthdata.VO;

import lombok.Data;

@Data
public class LoginResultVO {
    private String token;
    private String expireTime;
    private UserProfileVO user;
}