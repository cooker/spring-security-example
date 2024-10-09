package com.example.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * grant
 * 2024/10/9 10:55
 **/
@Data
@TableName("t_roles")
public class TRolesEntity {
    private Long id;
    private String name;
    private String roleCode;
    private Integer statusCd;
    private Integer deleteCd;
    private Date createTime;
}
