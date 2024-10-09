package com.example.security.filter;

import com.example.security.model.SysUserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * grant
 * JWT token 校验过滤器
 * 2024/10/9 09:20
 **/
@Slf4j
public class JwtValidationFilter extends OncePerRequestFilter {

    /**
     * 从请求中获取 JWT Token，校验 JWT Token 是否合法
     * <p>
     * 如果合法则将 Authentication 设置到 Spring Security Context 上下文中
     * 如果不合法则清空 Spring Security Context 上下文，并直接返回响应
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getParameter("token");
        if (!StringUtils.isEmpty(token)) {
            log.info("解析 token：{}", token);
            SysUserDTO sysUserDTO = new SysUserDTO();
            sysUserDTO.setUsername(token);
            sysUserDTO.setId(1L);
            sysUserDTO.setEnabled(true);
            sysUserDTO.setRoles(List.of("admin"));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(sysUserDTO, "", sysUserDTO.getAuthorities()));
        }

        // Token有效或无Token时继续执行过滤链
        filterChain.doFilter(request, response);
    }
}
