package com.example.security.model;

import com.example.security.config.SecurityConstants;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * grant
 * 2024/10/8 17:29
 **/
@Data
public class SysUserDTO implements UserDetails {
    private Long id;//id

    private String username;

    private String password;

    private List<String> roles;//角色

    private Set<String> perms;

    private boolean enabled;//是否开启

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!CollectionUtils.isEmpty(roles)) {
           return roles.stream().map(role->new SimpleGrantedAuthority(SecurityConstants.ROLE + role))
                    .collect(Collectors.toSet());
        }
        return List.of();
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
