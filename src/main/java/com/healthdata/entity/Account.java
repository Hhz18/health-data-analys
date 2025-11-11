package com.healthdata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.healthdata.enums.RoleType;
import lombok.Data;

@Data
@TableName("user") // 对应数据库表名user
public class Account {
    @TableId(value ="user_id", type = IdType.AUTO) // 自增主键
    private Integer userId;

    private String username;

    private String password;

    private String email; // 可为空

    private String phone; // 可为空

    private String address; // 可为空

    private RoleType role;

}