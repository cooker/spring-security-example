package com.example.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * grant
 * 2024/10/9 10:51
 **/
@Data
@TableName("t_users")
public class TUsersEntity {
    private Long id;
    private String nickname;
    private String img;
    private String username;
    private String password;
    private Integer statusCd;
    private Integer deleteCd;
    private Date createTime;
    private Date updateTime;
}
