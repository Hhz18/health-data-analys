package com.healthdata.controller;

import com.healthdata.service.CaptchaService;
import com.healthdata.VO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    // 生成验证码
    @GetMapping("/captcha")
    public CommonResponse<String> generateCaptcha(@RequestParam String username) {
        String captcha = captchaService.generateCaptcha(username);
        // 实际项目中，不直接返回验证码，而是通过短信/邮件发送
        // 这里为了测试，直接返回
        return CommonResponse.success(captcha, "验证码生成成功");
    }
}