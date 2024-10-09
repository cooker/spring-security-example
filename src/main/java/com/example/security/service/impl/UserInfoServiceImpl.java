package com.example.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.security.entity.TMenuEntity;
import com.example.security.entity.TRolePermissionEntity;
import com.example.security.entity.TRolesEntity;
import com.example.security.entity.TUserRolesEntity;
import com.example.security.entity.TUsersEntity;
import com.example.security.mapper.TMenuMapper;
import com.example.security.mapper.TRolePermissionMapper;
import com.example.security.mapper.TRolesMapper;
import com.example.security.mapper.TUserRolesMapper;
import com.example.security.mapper.TUsersMapper;
import com.example.security.model.SysUserDTO;
import com.example.security.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
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
    private final TMenuMapper menuMapper;
    private final TRolePermissionMapper rolePermissionMapper;


    @Override
    public SysUserDTO findUser(String username) {
        var user = usersMapper.selectOne(Wrappers.lambdaQuery(TUsersEntity.class).eq(TUsersEntity::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException(username + " 用户不存在");
        }
        var result = new SysUserDTO();
        result.setUsername(username);
        result.setPassword(user.getPassword());
        result.setId(user.getId());
        result.setEnabled(user.getStatusCd() == 1 && user.getDeleteCd() == 0);//是否启用
        var userRolesList = userRolesMapper.selectList(Wrappers.lambdaQuery(TUserRolesEntity.class).eq(TUserRolesEntity::getUid, user.getId()));
        //角色
        List roles = new LinkedList<>();
        if (!CollectionUtils.isEmpty(userRolesList)) {
            Set<Long> rIds = userRolesList.stream().map(TUserRolesEntity::getRid).collect(Collectors.toSet());
            var rolesList = rolesMapper.selectList(Wrappers.lambdaQuery(TRolesEntity.class)
                    .in(TRolesEntity::getId, rIds).eq(TRolesEntity::getStatusCd, 1).eq(TRolesEntity::getDeleteCd, 0));
            roles.addAll(rolesList.stream().map(TRolesEntity::getRoleCode).collect(Collectors.toSet()));

            //权限
            Set perms = new HashSet<>();
            var rolePermissionList = rolePermissionMapper.selectList(Wrappers.lambdaQuery(TRolePermissionEntity.class).in(TRolePermissionEntity::getRid, rIds));
            if (!CollectionUtils.isEmpty(rolePermissionList)) {
                Set<Long> mIds = rolePermissionList.stream().map(TRolePermissionEntity::getMid).collect(Collectors.toSet());
                var menuList = menuMapper.selectList(Wrappers.lambdaQuery(TMenuEntity.class).in(TMenuEntity::getId, mIds)
                        .eq(TMenuEntity::getTypeCd, TMenuEntity.TypeCdEnum.BUTTON.ordinal())
                        .eq(TMenuEntity::getStatusCd, 1)
                        .eq(TMenuEntity::getDeleteCd, 0));
                perms.addAll(menuList.stream().map(TMenuEntity::getUrl).collect(Collectors.toSet()));
            }
            result.setPerms(perms);
        }

        result.setRoles(roles);
        return result;
    }
}
