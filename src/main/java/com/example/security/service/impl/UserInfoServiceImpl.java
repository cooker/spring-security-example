package com.example.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.security.entity.TRolesEntity;
import com.example.security.entity.TUserRolesEntity;
import com.example.security.entity.TUsersEntity;
import com.example.security.mapper.TRolesMapper;
import com.example.security.mapper.TUserRolesMapper;
import com.example.security.mapper.TUsersMapper;
import com.example.security.model.SysUserDTO;
import com.example.security.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * grant
 * 2024/10/9 11:11
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final TUsersMapper usersMapper;
    private final TRolesMapper rolesMapper;
    private final TUserRolesMapper userRolesMapper;


    @Override
    public SysUserDTO findUser(String username) {
        TUsersEntity user = usersMapper.selectOne(Wrappers.lambdaQuery(TUsersEntity.class).eq(TUsersEntity::getUsername, username));
        SysUserDTO result = new SysUserDTO();
        result.setUsername(username);
        result.setPassword(user.getPassword());
        result.setId(user.getId());
        result.setEnabled(user.getStatusCd() == 1 && user.getDeleteCd() == 0);//是否启用
        List<TUserRolesEntity> userRolesList = userRolesMapper.selectList(Wrappers.lambdaQuery(TUserRolesEntity.class).eq(TUserRolesEntity::getUid, user.getId()));
        List<String> roles = new LinkedList<>();
        if (!CollectionUtils.isEmpty(userRolesList)) {
            Set<Long> rIds = userRolesList.stream().map(TUserRolesEntity::getRid).collect(Collectors.toSet());
            List<TRolesEntity> rolesList = rolesMapper.selectList(Wrappers.lambdaQuery(TRolesEntity.class)
                    .in(TRolesEntity::getId, rIds).eq(TRolesEntity::getStatusCd, 1).eq(TRolesEntity::getDeleteCd, 0));
            roles.addAll(rolesList.stream().map(TRolesEntity::getRoleCode).collect(Collectors.toSet()));
        }
        result.setRoles(roles);
        return result;
    }
}
