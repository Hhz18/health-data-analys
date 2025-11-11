package com.healthdata.enums;


public enum RoleType {
    ADMIN("管理员"),      // 管理员
    RESEARCHER("研究员"),  // 研究员
    ANALYST("分析师"),    // 分析师
    AUDITOR("审核员"),    // 审核员
    USER("普通用户"),     // 普通用户
    GUEST("游客");       // 游客

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}