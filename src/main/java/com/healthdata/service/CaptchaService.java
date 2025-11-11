package com.healthdata.service;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {

    @Autowired
//    private StringRedisTemplate redisTemplate;
    private StringRedisTemplate redisTemplate;

    // 生成验证码并存储（5分钟过期）
    public String generateCaptcha(String username) {
        String captcha = String.valueOf(new Random().nextInt(900000) + 100000); // 6位数字
        redisTemplate.opsForValue().set(
                "captcha:" + username,
                captcha,
                5,
                TimeUnit.MINUTES
        );
        return captcha;
    }

    // 验证验证码
    public boolean validateCaptcha(String username, String captcha) {
        String storedCaptcha = redisTemplate.opsForValue().get("captcha:" + username);
        return captcha != null && captcha.equals(storedCaptcha);
    }
}