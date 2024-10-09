package com.example.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * grant
 * 2024/10/9 10:56
 **/
@Data
@TableName("t_user_roles")
public class TUserRolesEntity {
    private Long id;
    private Long uid;
    private Long rid;
    private Date createTime;
}
