package com.example.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * grant
 * 2024/10/9 11:43
 **/
@Data
@TableName("t_menu")
public class TMenuEntity {
    private Long id;
    private Long pid;
    private String name;
    private String url;
    private String img;
    private Integer statusCd;
    private Integer typeCd;//类型 0-父菜单 1-子菜单 2-功能
    private Integer deleteCd;
    private Date createTime;
    private Date updateTime;
}
