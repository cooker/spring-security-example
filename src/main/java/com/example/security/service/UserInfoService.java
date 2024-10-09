package com.example.security.service;

import com.example.security.model.SysUserDTO;

public interface UserInfoService {

    SysUserDTO findUser(String username);
}
