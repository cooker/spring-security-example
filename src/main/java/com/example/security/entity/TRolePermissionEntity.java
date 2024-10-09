package com.example.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * grant
 * 2024/10/9 11:29
 **/
@Data
@TableName("t_role_permission")
public class TRolePermissionEntity {
    private Long id;
    private Long rid;
    private Long mid;
    private Date createTime;
}
